package com.hust.hui.quickmedia.common.test.img;

import com.hust.hui.quickmedia.common.img.create.ImgCreateOptions;
import com.hust.hui.quickmedia.common.img.merge.cell.IMergeCell;
import com.hust.hui.quickmedia.common.img.merge.cell.ImgCell;
import com.hust.hui.quickmedia.common.img.merge.cell.LineCell;
import com.hust.hui.quickmedia.common.img.merge.cell.TextCell;
import com.hust.hui.quickmedia.common.test.img.merge.QrCodeCardTemplate;
import com.hust.hui.quickmedia.common.test.img.merge.QrCodeCardTemplateBuilder;
import com.hust.hui.quickmedia.common.util.FontUtil;
import com.hust.hui.quickmedia.common.util.GraphicUtil;
import com.hust.hui.quickmedia.common.util.ImageExUtil;
import com.hust.hui.quickmedia.common.util.ImageUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yihui on 2017/10/13.
 */
public class ImgMergeWrapperTest {

    @Test
    public void testCell() throws IOException {
        int w = 520, h = 260;
        BufferedImage bg = new BufferedImage(520, 260, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = GraphicUtil.getG2d(bg);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);


        List<IMergeCell> list = new ArrayList<>();

        // logo
        BufferedImage logo = ImageUtil.getImageByPath("bg.png");
        logo = ImageExUtil.makeRoundImg(logo, false, null);
        ImgCell logoCell = ImgCell.builder()
                .img(logo)
                .x(60)
                .y(20)
                .w(100)
                .h(100)
                .build();
        list.add(logoCell);


        // 文案
        TextCell textCell = new TextCell();
        textCell.setFont(new Font("宋体", Font.BOLD, 22));
        textCell.setColor(Color.BLACK);
        textCell.setStartX(20);
        textCell.setStartY(140);
        textCell.setEndX(220);
        textCell.setEndY(180);
        textCell.addText("小灰灰Blog");
        textCell.setDrawStyle(ImgCreateOptions.DrawStyle.HORIZONTAL);
        textCell.setAlignStyle(ImgCreateOptions.AlignStyle.CENTER);
        list.add(textCell);


        // 说明文案
        TextCell descCell = new TextCell();
        descCell.setFont(FontUtil.DEFAULT_FONT);
        descCell.setColor(Color.GRAY);
        descCell.setStartX(20);
        descCell.setStartY(180);
        descCell.setEndX(220);
        descCell.setEndY(240);
        descCell.addText("我是小灰灰Blog，哒哒");
        descCell.addText("下一行，小尾巴，哒哒哒");
        descCell.setDrawStyle(ImgCreateOptions.DrawStyle.HORIZONTAL);
        descCell.setAlignStyle(ImgCreateOptions.AlignStyle.CENTER);
        list.add(descCell);


        // line
        LineCell line = LineCell.builder().x1(280)
                .y1(20)
                .x2(240)
                .y2(240)
                .color(Color.GRAY)
                .build();
        list.add(line);


        BufferedImage qrCode = ImageUtil.getImageByPath("/Users/yihui/Desktop/xcx.jpg");
        ImgCell imgCell = ImgCell.builder()
                .img(qrCode)
                .x(300)
                .y(30)
                .w(200)
                .h(200)
                .build();
        list.add(imgCell);


        list.stream().forEach(s -> s.draw(g2d));

        System.out.println("---绘制完成---");
        try {
            ImageIO.write(bg, "jpg", new File("/Users/yihui/Desktop/merge.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTemplate() throws IOException {
        BufferedImage logo = ImageUtil.getImageByPath("logo.jpg");
        BufferedImage qrCode = ImageUtil.getImageByPath("/Users/yihui/Desktop/12.jpg");
        String name = "小灰灰BLog";
        List<String> desc = Arrays.asList("我是一灰灰，一匹不吃羊的狼", "专注码农技术分享");


        int w = QrCodeCardTemplate.w, h = QrCodeCardTemplate.h;
        BufferedImage bg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = GraphicUtil.getG2d(bg);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);


        List<IMergeCell> list = QrCodeCardTemplateBuilder.build(logo, name, desc, qrCode);
        list.stream().forEach(s -> s.draw(g2d));


        System.out.println("---绘制完成---");
        try {
            String text = "微 信 公 众 号";
            Font font = FontUtil.getFont("font/txlove.ttf", Font.ITALIC, 20);
            FontMetrics metrics = FontUtil.getFontMetric(font);
            int tw = metrics.stringWidth(text);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);

            g2d.fillRect((w - tw - metrics.getHeight() - metrics.getHeight()) >>> 1, 10, tw + metrics.getHeight() * 2, 20);

            g2d.setColor(Color.GRAY);
            g2d.drawString(text, (w - tw) >>> 1, 20 + font.getSize() / 2 - metrics.getDescent());


            ImageIO.write(bg, "jpg", new File("/Users/yihui/Desktop/merge.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
