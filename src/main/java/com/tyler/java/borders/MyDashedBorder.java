package com.tyler.java.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.DashedBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyDashedBorder extends DashedBorder {
    private static Logger logger = LoggerFactory.getLogger(MyDashedBorder.class);

    public MyDashedBorder(float width) {
        super(width);
    }

    public MyDashedBorder(Color color, float width) {
        super(color, width);
    }

    public MyDashedBorder(Color color, float width, float opacity) {
        super(color, width, opacity);
    }

    @Override
    public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Side defaultSide) {
        logger.debug("1:({}, {}); 2:({}, {})", x1, y1, x2, y2);
        float initialGap = 2;
        float dash = 5;
        float dx = x2 - x1;
        float dy = y2 - y1;
        double borderLength = Math.sqrt(dx * dx + dy * dy);

        float adjustedGap = getDotsGap(borderLength, initialGap + dash);
        if (adjustedGap > dash) {
            adjustedGap -= dash;
        }

        canvas.
                saveState().
                setStrokeColor(transparentColor.getColor());
        transparentColor.applyStrokeTransparency(canvas);
        canvas.
                setLineDash(dash, adjustedGap, dash + adjustedGap / 2).
                setLineWidth(width).
                moveTo(x1, y1).
                lineTo(x2, y2).
                stroke().
                restoreState();
    }
}
