/*
 * Created by JFormDesigner on Sun Dec 30 20:41:55 EET 2012
 */

package gui;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import element.Chapter;
import element.ChapterIterator;

/**
 * @author Andrei Datcu
 */
public class PagePanel extends JPanel {
		private ChapterIterator startIt, curIt;
		private String text;
		private Chapter ch;
		private Hashtable<TextAttribute, Object> attributeMap;
		private Font font;
		private BufferedImage img;
		private final int LEFT = 0;
		private final int CENTER = 1;
		private int characterIndex;
		private String title, header;
		private int number;
		private ArrayList<Integer> matchPositions;
		private boolean imagePrinted;
		
		public PagePanel(ChapterIterator it, Chapter ch, Font font, String title, String header, int number,
				int width, int length){
			this.startIt = it;
			this.ch = ch;
			this.img = null;
			this.font = font;
			this.title = title;
			this.header = header;
			this.number = number;
			this.setLayout(null);
			this.setPreferredSize(new Dimension(width, length));
			this.setMaximumSize(new Dimension(width, length));
			this.setMinimumSize(new Dimension(width, length));
			this.setBorder(new LineBorder(Color.black));
			paintPageToImage(null, false);
		}
		
		public ArrayList<Integer> searchAndHighlightText(String text, boolean caseSensitive){
			matchPositions = new ArrayList<Integer>();
			paintPageToImage(text, caseSensitive);
			repaint();
			return matchPositions;
		}
		
		public void stopSearch(){
			paintPageToImage(null, false);
			repaint();
		}
		
		public ChapterIterator getChapterIterator(){
			return this.curIt;
		}
		
		private float paintParagraph(Graphics2D g2d, String printString, boolean alineat, int allign,
					float startPosX, float startPosY, float height, float breakWidth, String matchedText, boolean caseSensitive){
			if (printString.length() == 0)
				return startPosY;
			if (allign == CENTER)
				breakWidth /= 2;
			Pattern searchPattern = null;
			Matcher match = null;
			AttributedString print = new AttributedString(printString, attributeMap);
			ArrayList<Integer> matchedStarts = new ArrayList<Integer>();
			if (matchedText != null){
				if (caseSensitive)
					searchPattern = Pattern.compile(matchedText);
				else
					searchPattern = Pattern.compile(matchedText, Pattern.CASE_INSENSITIVE);
				match = searchPattern.matcher(printString);
				while (match.find()){
					print.addAttribute(TextAttribute.SWAP_COLORS, TextAttribute.SWAP_COLORS_ON, match.start(), match.end());
					matchedStarts.add(match.start());
				}
			}
			int i = 0, endIndex = 0;
			AttributedCharacterIterator paragraph = print.getIterator();
			int paragraphStart = paragraph.getBeginIndex();
			int paragraphEnd = paragraph.getEndIndex();
			FontRenderContext frc = g2d.getFontRenderContext();
			LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);
			lineMeasurer.setPosition(paragraphStart);
			float breakWidthFirst = breakWidth - 3 * (Float)attributeMap.get(TextAttribute.SIZE);
			int line = 0;
			float drawPosY = startPosY, drawPosX;
			while (drawPosY < height - (float)1.1 * (Float)attributeMap.get(TextAttribute.SIZE) && lineMeasurer.getPosition() < paragraphEnd) {
				if (matchedText != null){
					endIndex = lineMeasurer.nextOffset(alineat ? line == 0 ? breakWidthFirst : breakWidth : breakWidth);
					for (;i < matchedStarts.size() && matchedStarts.get(i) < endIndex; ++i)
						matchPositions.add(Math.round(drawPosY));
				}
				TextLayout layout = lineMeasurer.nextLayout(alineat ? line == 0 ? breakWidthFirst : breakWidth : breakWidth);
				if (allign == CENTER)
					drawPosX = startPosX + breakWidth - (float)layout.getBounds().getWidth() / 2;
				else
					if (!alineat)
						drawPosX = startPosX;
					else
						drawPosX = startPosX + (line == 0 ? 3 * (Float)attributeMap.get(TextAttribute.SIZE) : 0); //alineat sau nu?
				drawPosY += layout.getAscent();
				layout.draw(g2d, drawPosX, drawPosY);
				drawPosY += layout.getDescent() + layout.getLeading();
				line++;
			}
			characterIndex += lineMeasurer.getPosition() - paragraphStart; //pana la ce caracter am scris?
			return drawPosY;
		}
		
		private float paintImage(Graphics2D g2d, String path, int yPos, int xPos, int height){
			try {
				imagePrinted = false;
				BufferedImage img = ImageIO.read(new File(path));
				if (yPos + img.getHeight() <= height){
					g2d.drawImage(img, null, xPos, yPos);
					imagePrinted = true;
				}
				yPos += img.getHeight() + 10;
				return (float)yPos;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return height;
			}
			
		}
		
		private void paintPageToImage(String matchText, boolean caseSensitive){
			curIt = new ChapterIterator();
			curIt.paragraphIndex = startIt.paragraphIndex;
			curIt.charIndex = startIt.charIndex;
			
			img = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)img.getGraphics();
			g2d.setBackground(Color.white);
			g2d.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
			g2d.setPaint(Color.black);
			g2d.setFont(font);
			attributeMap = new Hashtable<TextAttribute, Object>();
			attributeMap.put(TextAttribute.FAMILY, font.getFamily());
	        attributeMap.put(TextAttribute.SIZE, new Float((float)font.getSize()));
	        attributeMap.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
	        
	        int fontSize = font.getSize();
	        
	        //printez antet
	        
	        float yPos = 5 * fontSize;
	        float xPos = 5 * fontSize;
	        float breakWidth = getPreferredSize().width - 8 * fontSize;
	        float height = getPreferredSize().height - 5 * fontSize; //cat poate sa fie maxim de la antet pana la subsol exclusiv
	        
	        yPos = paintParagraph(g2d, header, false, LEFT,xPos + 3 * fontSize, yPos, height, breakWidth - 3 * fontSize, matchText, caseSensitive);
	        //trag linie sub antet
	        g2d.drawLine(Math.round(xPos), Math.round(yPos), Math.round(xPos + breakWidth), Math.round(yPos));
	        attributeMap.remove(TextAttribute.POSTURE);
	        //spatiu intre antet si titlu
	        yPos += 5 * fontSize;
	        
	        if (curIt.paragraphIndex == 0 && curIt.charIndex == 0){//printez titlu?
	        	attributeMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
	        	yPos = paintParagraph(g2d, title, false, CENTER, 0, yPos, height, img.getWidth(), matchText, caseSensitive);
	        	yPos += 6 * fontSize;
	        	attributeMap.remove(TextAttribute.WEIGHT);
	        }
	        
	        //printez continut
	        
	        while (curIt.paragraphIndex < ch.size() &&  height - yPos > fontSize){
	        	characterIndex = curIt.charIndex;
	        	if (ch.getParagraph(curIt.paragraphIndex).isImageParagraph())
	        		yPos = paintImage(g2d, ch.getParagraph(curIt.paragraphIndex).getImagePath(), Math.round(yPos), Math.round(xPos), Math.round(height));
	        	else
	        	yPos = paintParagraph(g2d, ch.getParagraph(curIt.paragraphIndex).toString().substring(curIt.charIndex), curIt.charIndex == 0 ? true : false,
	        			LEFT, xPos, yPos, height, breakWidth, matchText, caseSensitive)
	        			+ fontSize / 2;
	        	curIt.charIndex = 0;
	        	curIt.paragraphIndex++;
	        }
	        
	        if (ch.getParagraph(curIt.paragraphIndex - 1).isImageParagraph()){
	        	if (!imagePrinted)
	        		curIt.paragraphIndex--;
	        }
	        else
	        if (ch.getParagraph(curIt.paragraphIndex - 1).getCharCount() > characterIndex){//ultimul paragraf a fost scris tot?
	        	curIt.paragraphIndex--;
	        	curIt.charIndex = characterIndex;
	        }
	        
	        
	        //printez nr. paginii
	        
	        String nr = ""+number;
	        g2d.drawChars(nr.toCharArray(), 0, nr.length(), Math.round(breakWidth), Math.round(height + 2*fontSize));
	        
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			setBackground(Color.white);
			if (img != null){
				g.drawImage(img, 0, 0, null);
				return;
			}
			paintPageToImage(null, false);//n-ar trebui sa se ajugna aici
			paintComponent(g);
		}
}