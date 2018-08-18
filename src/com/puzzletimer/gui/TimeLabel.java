package com.puzzletimer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class TimeLabel extends JComponent {
    private String text;

    public TimeLabel(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int textWidth = g2.getFontMetrics().stringWidth("00:00.000");
        int fontSize = getFont().getSize() * getWidth() / textWidth;
        g2.setFont(getFont().deriveFont((float) Math.min(fontSize, getHeight())));

        Rectangle2D bounds = g2.getFontMetrics().getStringBounds(this.text, g2);
        g2.drawString(
                this.text,
                (int) ((getWidth() - bounds.getWidth()) / 2 - bounds.getX()),
                (int) ((getHeight() - bounds.getHeight()) / 2 - bounds.getY()));
    }
}
