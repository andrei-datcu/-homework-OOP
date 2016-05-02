package gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import structures.DetailedShowInfo;
import urlreaders.DetailedShowInfoURLReader;
import urlreaders.DetailedShowInfoXmlParser;

public class DetailedShowInfoPanel extends JPanel{
	
	private JLabel imageLabel, titleLabel, periodLabel, networkLabel, timeLabel, akaLabel;
	private LinkLabel tvrageLinkLabel;
	  
	private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D
		g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}
	  
	private BufferedImage resizeImage(BufferedImage srcImage, int maxWidth, int maxHeight){
		if (srcImage.getWidth() <= maxWidth && srcImage.getHeight() <= maxHeight)
			return srcImage;
		float r = (float)srcImage.getWidth() / (float)srcImage.getHeight();
		if ((float)maxWidth / r <= maxHeight)
			return getScaledImage(srcImage, maxWidth, Math.round((float) maxWidth / r));
		else
			return getScaledImage(srcImage, Math.round(r * maxHeight), maxHeight);
	}
	
	public DetailedShowInfoPanel(String xml){
		setLayout(new GridBagLayout());
		try {
			DetailedShowInfo info = DetailedShowInfoXmlParser.parse(xml);
			imageLabel = new JLabel();
			imageLabel.setIcon(new ImageIcon(resizeImage(ImageIO.read(new URL(info.imageURL)), 300, 300)));
			add(imageLabel, new GridBagConstraints(0, 0, 1, 6, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
			titleLabel = new JLabel(info.name);
			titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize() * 2));
			add(titleLabel, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
			periodLabel = new JLabel("("+info.started+" - "+info.ended+")");
			add(periodLabel, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 0, 0), 0, 0));
			networkLabel = new JLabel("Televiziune: " + info.network);
			add(networkLabel, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
			timeLabel = new JLabel("Ruleaza: "+info.airDay +", de la ora " + info.airTime);
			add(timeLabel, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
			String akas = "<html>Cunoscut si ca<ul>";
			for (int i = 0; i < info.akas.size(); ++i)
				akas += "<li>"+info.akas.get(i)+"</li>";
			akas += "</ul></html>";
			akaLabel = new JLabel(akas);
			add(akaLabel, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
			tvrageLinkLabel = new LinkLabel("Link TVRage", info.tvrageURL, "Apasa pentru a vedea mai multe detalii pe www.tvrage.com");
			add(tvrageLinkLabel, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 0, 0), 0, 0));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
