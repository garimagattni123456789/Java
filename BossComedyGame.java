
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Arrays;

public class BossComedyGame extends JPanel implements MouseListener {

    private final Color navy = new Color(12, 29, 49);
    private final Color gold = new Color(255, 211, 61);

    private final String[] objects = {
        "COFFEE", "PAPERS", "OFFICE FAN", "BANANA PEEL",
        "LAPTOP", "WATER COOLER", "PLANT", "STAPLER",
        "RECYCLING BIN", "VACUUM"
    };

    private final String[] endings = {
        "THE DECAF DISASTER",
        "THE PAPER BLIZZARD",
        "THE EXECUTIVE WIND TUNNEL",
        "THE BANANA BALLET",
        "THE UPDATE THAT NEVER ENDS",
        "THE HYDRATION PRESENTATION",
        "THE FERN-TASTIC PROMOTION",
        "THE STAPLER OF DESTINY",
        "THE GREAT PAPERWORK ESCAPE",
        "THE ROOMBA REBELLION"
    };

    private final String[] jokes = {
        "The boss discovers his coffee is decaf. Emergency nap declared!",
        "Reports fly everywhere. The boss emerges wearing a paper hat.",
        "A gust launches paperwork into a spectacular office tornado!",
        "The boss performs a cartoon spin and lands safely in his chair.",
        "The laptop says Updating: 99%. The meeting enters another century.",
        "The water cooler gurgles. The boss bows, thinking it is applause.",
        "A leaf lands on the boss's head. The plant is promoted to manager.",
        "A paperclip attaches the boss's tie to a 90-page memo.",
        "Old reports reach recycling. The boss discovers Friday is free!",
        "The vacuum steals the boss's tie and rolls away like a getaway car!"
    };

    private final boolean[] discovered = new boolean[10];
    private final Rectangle[] objectBounds = new Rectangle[10];

    private final Rectangle gloveBounds = new Rectangle();
    private final Rectangle confirmBounds = new Rectangle();
    private final Rectangle retryBounds = new Rectangle();
    private final Rectangle menuBounds = new Rectangle();

    private int score = 0;
    private int discoveries = 0;
    private int selected = -1;

    private int punchFrame = 0;
    private boolean punching = false;
    private boolean bossReacting = false;

    private Timer punchTimer;

    private String message =
        "Welcome! Click an office object or use the boxing gloves.";

    private String ending = "YOUR FIRST OFFICE ADVENTURE";
    private String speech = "Where is my report?!";

    public BossComedyGame() {
        setPreferredSize(new Dimension(1100, 720));
        setBackground(navy);
        addMouseListener(this);

        for (int i = 0; i < objectBounds.length; i++) {
            objectBounds[i] = new Rectangle();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawHeader(g);
        drawOffice(g);
        drawGloveButton(g);
        drawPunchAnimation(g);
        drawObjects(g);
        drawSidebar(g);

        g.dispose();
    }

    private void drawHeader(Graphics2D g) {
        g.setColor(new Color(7, 18, 33));
        g.fillRect(0, 0, getWidth(), 82);

        g.setFont(new Font("SansSerif", Font.BOLD, 27));
        g.setColor(gold);
        g.drawString("1000 FUNNY WAYS", 22, 36);

        g.setColor(Color.WHITE);
        g.drawString("TO OUTSMART YOUR BOSS!", 22, 66);

        g.setFont(new Font("SansSerif", Font.ITALIC, 13));
        g.setColor(new Color(180, 220, 250));
        g.drawString("FICTIONAL OFFICE COMEDY", 800, 45);
    }

    private void drawOffice(Graphics2D g) {
        // Office wall
        g.setColor(new Color(218, 229, 237));
        g.fillRect(0, 82, 790, 638);

        // Window and sky
        g.setColor(new Color(115, 203, 245));
        g.fillRoundRect(200, 120, 365, 210, 12, 12);

        // City buildings
        g.setColor(new Color(108, 157, 188));
        g.fillRect(225, 220, 65, 110);
        g.fillRect(300, 185, 85, 145);
        g.fillRect(395, 235, 60, 95);
        g.fillRect(465, 195, 75, 135);

        g.setColor(new Color(224, 244, 255));

        for (int x = 235; x < 535; x += 28) {
            for (int y = 210; y < 315; y += 30) {
                g.fillRect(x, y, 10, 12);
            }
        }

        // Window frame
        g.setColor(new Color(245, 248, 251));
        g.setStroke(new BasicStroke(7));
        g.drawRect(200, 120, 365, 210);
        g.drawLine(380, 120, 380, 330);
        g.drawLine(200, 225, 565, 225);

        // Office poster
        g.setColor(new Color(255, 249, 216));
        g.fillRoundRect(35, 145, 125, 110, 5, 5);

        g.setColor(navy);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString("WORK HARD", 47, 180);
        g.drawString("DREAM BIG", 47, 204);
        g.drawString("TAKE LUNCH!", 42, 228);

        // Bookshelf
        g.setColor(new Color(115, 70, 43));
        g.fillRoundRect(620, 135, 135, 205, 6, 6);

        Color[] bookColors = {
            new Color(55, 115, 155),
            new Color(190, 73, 74),
            new Color(226, 181, 62),
            new Color(67, 137, 99),
            new Color(102, 85, 151)
        };

        for (int shelf = 0; shelf < 3; shelf++) {
            int y = 150 + shelf * 62;

            for (int i = 0; i < 5; i++) {
                g.setColor(bookColors[(i + shelf) % bookColors.length]);
                g.fillRoundRect(630 + i * 23, y, 18, 48, 3, 3);
            }

            g.setColor(new Color(218, 163, 106));
            g.fillRect(625, y + 50, 125, 5);
        }

        // Floor
        g.setColor(new Color(79, 111, 139));
        g.fillRect(0, 475, 790, 245);

        g.setColor(new Color(100, 133, 158));

        for (int y = 490; y < 720; y += 48) {
            g.drawLine(0, y, 790, y);
        }

        for (int x = 0; x < 790; x += 95) {
            g.drawLine(x, 475, x - 35, 720);
        }

        // Desk
        g.setColor(new Color(111, 62, 36));
        g.fillRoundRect(165, 415, 515, 155, 8, 8);

        g.setColor(new Color(202, 135, 72));
        g.fillRoundRect(145, 395, 555, 43, 7, 7);

        g.setColor(new Color(233, 169, 103));
        g.fillRect(160, 400, 525, 7);

        // Desk drawers
        g.setColor(new Color(133, 77, 43));
        g.fillRoundRect(250, 465, 130, 82, 5, 5);
        g.fillRoundRect(455, 465, 130, 82, 5, 5);

        g.setColor(new Color(233, 183, 117));
        g.fillRoundRect(290, 485, 45, 7, 3, 3);
        g.fillRoundRect(495, 485, 45, 7, 3, 3);

        drawBoss(g);
    }

    private void drawBoss(Graphics2D g) {
        Graphics2D b = (Graphics2D) g.create();

        // Shake the boss during the hit reaction.
        int shakeX = 0;
        int shakeY = 0;

        if (bossReacting) {
            shakeX = (punchFrame % 2 == 0) ? 9 : -9;
            shakeY = (punchFrame % 2 == 0) ? -3 : 3;
        }

        b.translate(shakeX, shakeY);

        // Body
        b.setColor(new Color(35, 52, 78));
        b.fillRoundRect(350, 326, 120, 95, 25, 25);

        // Shirt
        b.setColor(new Color(245, 243, 231));

        int[] sx = {390, 410, 430, 420, 400};
        int[] sy = {330, 355, 330, 412, 412};

        b.fillPolygon(sx, sy, sx.length);

        // Tie
        b.setColor(new Color(195, 48, 58));

        int[] tx = {402, 418, 414, 421, 400, 406};
        int[] ty = {335, 335, 350, 405, 405, 350};

        b.fillPolygon(tx, ty, tx.length);

        // Ears
        b.setColor(new Color(238, 166, 111));
        b.fillOval(335, 270, 22, 30);
        b.fillOval(456, 270, 22, 30);

        // Face
        b.setColor(new Color(255, 195, 143));
        b.fillOval(344, 245, 126, 112);

        // Hair
        b.setColor(new Color(48, 40, 39));

        int[] hx = {
            343, 348, 360, 355, 378, 390, 410, 432,
            456, 467, 457, 444, 425, 405, 382, 360
        };

        int[] hy = {
            276, 250, 240, 225, 239, 218, 237, 226,
            243, 272, 261, 255, 259, 254, 260, 278
        };

        b.fillPolygon(hx, hy, hx.length);

        // Eyebrows
        b.setColor(new Color(54, 39, 35));
        b.setStroke(new BasicStroke(5));

        if (bossReacting) {
            // Raised eyebrows for a surprised expression
            b.drawLine(365, 269, 389, 265);
            b.drawLine(421, 265, 445, 269);
        } else {
            b.drawLine(365, 272, 389, 279);
            b.drawLine(421, 279, 445, 271);
        }

        // Eyes
        b.setColor(Color.WHITE);
        b.fillOval(369, 278, 20, 17);
        b.fillOval(420, 278, 20, 17);

        b.setColor(Color.BLACK);
        b.fillOval(378, 283, 8, 10);
        b.fillOval(423, 283, 8, 10);

        // Nose
        b.setColor(new Color(225, 137, 91));
        b.fillOval(399, 290, 18, 22);

        // Mouth changes when the boss is hit.
        b.setColor(new Color(119, 45, 44));
        b.setStroke(new BasicStroke(4));

        if (bossReacting) {
            b.drawOval(394, 313, 25, 22);
        } else {
            b.drawArc(384, 314, 43, 23, 200, 140);
        }

        // Nameplate
        b.setColor(gold);
        b.fillRoundRect(360, 411, 105, 25, 5, 5);

        b.setColor(navy);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.drawString("THE BOSS", 376, 428);

        b.dispose();

        // Speech bubble
        g.setColor(Color.WHITE);
        g.fillRoundRect(465, 245, 205, 45, 12, 12);

        g.setColor(navy);
        g.setFont(new Font("SansSerif", Font.BOLD, 11));

        FontMetrics fm = g.getFontMetrics();

        String displayedSpeech = speech;

        while (fm.stringWidth(displayedSpeech) > 185
                && displayedSpeech.length() > 4) {
            displayedSpeech =
                displayedSpeech.substring(0, displayedSpeech.length() - 1);
        }

        g.drawString(displayedSpeech, 475, 272);
    }

    private void drawGloveButton(Graphics2D g) {
        gloveBounds.setBounds(570, 350, 110, 40);

        g.setColor(new Color(190, 45, 55));
        g.fillRoundRect(
            gloveBounds.x, gloveBounds.y,
            gloveBounds.width, gloveBounds.height, 12, 12
        );

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString("PUNCH! +25", 582, 374);

        g.setColor(new Color(255, 170, 170));
        g.drawRoundRect(
            gloveBounds.x, gloveBounds.y,
            gloveBounds.width, gloveBounds.height, 12, 12
        );
    }

    private void drawPunchAnimation(Graphics2D g) {
        if (!punching) {
            return;
        }

        // Glove travels from right to left toward the boss.
        int gloveX = 585 - punchFrame * 15;
        int gloveY = 270;

        // Speed streaks
        g.setColor(new Color(255, 255, 255, 180));
        g.setStroke(new BasicStroke(3));

        if (punchFrame < 8) {
            g.drawLine(gloveX + 25, gloveY + 5, gloveX + 48, gloveY);
            g.drawLine(gloveX + 25, gloveY + 18, gloveX + 52, gloveY + 18);
            g.drawLine(gloveX + 25, gloveY + 30, gloveX + 46, gloveY + 36);
        }

        // Gold cuff
        g.setColor(new Color(245, 190, 60));
        g.fillRoundRect(gloveX + 17, gloveY + 14, 28, 23, 7, 7);

        // Red glove
        g.setColor(new Color(220, 35, 50));
        g.fillOval(gloveX, gloveY, 37, 36);
        g.fillOval(gloveX + 18, gloveY + 8, 28, 27);

        // Glove highlight
        g.setColor(new Color(255, 130, 130));
        g.drawArc(gloveX + 5, gloveY + 5, 18, 16, 30, 120);

        // Impact effect
        if (bossReacting) {
            g.setColor(gold);
            g.setFont(new Font("SansSerif", Font.BOLD, 25));
            g.drawString("POW!", 425, 232);

            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString("*", 410, 260);
            g.drawString("*", 470, 250);
            g.drawString("*", 450, 290);
        }
    }

    private void drawObjects(Graphics2D g) {
        int startX = 15;
        int startY = 590;
        int w = 145;
        int h = 52;
        int gap = 9;

        for (int i = 0; i < objects.length; i++) {
            int row = i / 5;
            int col = i % 5;

            int x = startX + col * (w + gap);
            int y = startY + row * 60;

            objectBounds[i].setBounds(x, y, w, h);

            g.setColor(i == selected
                ? gold
                : new Color(16, 43, 68));

            g.fillRoundRect(x, y, w, h, 12, 12);

            g.setColor(new Color(74, 119, 151));
            g.drawRoundRect(x, y, w, h, 12, 12);

            g.setColor(i == selected ? navy : Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));

            FontMetrics fm = g.getFontMetrics();

            int textX = x + (w - fm.stringWidth(objects[i])) / 2;
            g.drawString(objects[i], textX, y + 30);

            if (discovered[i]) {
                g.setColor(new Color(100, 255, 150));
                g.fillOval(x + w - 19, y + 5, 10, 10);
            }
        }
    }

    private void drawSidebar(Graphics2D g) {
        int x = 800;
        int w = 285;

        g.setColor(new Color(7, 20, 36));
        g.fillRoundRect(x, 95, w, 600, 18, 18);

        // Mission card
        g.setColor(gold);
        g.fillRoundRect(x + 12, 108, w - 24, 42, 10, 10);

        g.setColor(navy);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString("YOUR OFFICE MISSION", x + 27, 135);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("CURRENT ENDING", x + 20, 181);

        g.setColor(gold);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        drawWrapped(g, ending, x + 20, 208, w - 40, 21);

        g.setColor(new Color(195, 225, 247));
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        drawWrapped(g, message, x + 20, 270, w - 40, 20);

        // Score panel
        g.setColor(new Color(22, 49, 75));
        g.fillRoundRect(x + 15, 385, w - 30, 78, 12, 12);

        g.setColor(gold);
        g.setFont(new Font("SansSerif", Font.BOLD, 23));
        g.drawString("SCORE: " + score, x + 30, 417);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString(
            "Ways discovered: " + discoveries + " / 1000",
            x + 30, 443
        );

        // Sidebar buttons
        confirmBounds.setBounds(x + 15, 480, w - 30, 42);
        retryBounds.setBounds(x + 15, 531, w - 30, 42);
        menuBounds.setBounds(x + 15, 582, w - 30, 42);

        drawButton(
            g, confirmBounds, "LOG THIS ENDING",
            new Color(60, 185, 94)
        );

        drawButton(
            g, retryBounds, "TRY AGAIN [ +10 ]",
            new Color(36, 139, 220)
        );

        drawButton(
            g, menuBounds, "RESET GAME",
            new Color(135, 77, 215)
        );

        g.setColor(new Color(185, 213, 234));
        g.setFont(new Font("SansSerif", Font.ITALIC, 11));
        g.drawString("Cartoon comedy only!", x + 65, 661);
        g.drawString("Click objects to discover gags.", x + 37, 678);
    }

    private void drawButton(
            Graphics2D g, Rectangle r, String text, Color color) {

        g.setColor(color);
        g.fillRoundRect(r.x, r.y, r.width, r.height, 12, 12);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));

        FontMetrics fm = g.getFontMetrics();

        int tx = r.x + (r.width - fm.stringWidth(text)) / 2;
        int ty = r.y + (r.height + fm.getAscent()) / 2 - 3;

        g.drawString(text, tx, ty);
    }

    private void drawWrapped(
            Graphics2D g, String text,
            int x, int y, int maxWidth, int lineHeight) {

        FontMetrics fm = g.getFontMetrics();
        String[] words = text.split(" ");

        String line = "";
        int currentY = y;

        for (String word : words) {
            String test = line.isEmpty() ? word : line + " " + word;

            if (fm.stringWidth(test) > maxWidth && !line.isEmpty()) {
                g.drawString(line, x, currentY);
                currentY += lineHeight;
                line = word;
            } else {
                line = test;
            }
        }

        if (!line.isEmpty()) {
            g.drawString(line, x, currentY);
        }
    }

    private void startPunch() {
        if (punching) {
            return;
        }

        punching = true;
        bossReacting = false;
        punchFrame = 0;

        score += 25;
        ending = "THE OFFICE BOXING CHAMPION";
        message = "A boxing glove flies toward the boss!";
        speech = "HEY! WATCH THOSE GLOVES!";

        punchTimer = new Timer(45, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                punchFrame++;

                if (punchFrame >= 8) {
                    bossReacting = true;
                    speech = "WHO ORDERED THAT?!";
                }

                if (punchFrame >= 12) {
                    punchTimer.stop();
                    punching = false;
                    bossReacting = false;

                    speech = "BACK TO WORK!";
                    message =
                        "POW! The boss survived another cartoon surprise!";
                }

                repaint();
            }
        });

        punchTimer.start();
        repaint();
    }

    private void chooseObject(int index) {
        selected = index;

        if (!discovered[index]) {
            discovered[index] = true;
            discoveries++;
            score += 100;
        } else {
            score += 10;
        }

        ending = endings[index];
        message = jokes[index];

        switch (index) {
            case 0:
                speech = "THIS IS DECAF?!";
                break;
            case 1:
                speech = "MY REPORTS!";
                break;
            case 2:
                speech = "WHO TURNED THAT ON?!";
                break;
            case 3:
                speech = "I MEANT TO DO THAT!";
                break;
            case 4:
                speech = "NOT ANOTHER UPDATE!";
                break;
            case 5:
                speech = "THANK YOU, TEAM!";
                break;
            case 6:
                speech = "PROMOTE THE PLANT!";
                break;
            case 7:
                speech = "NOBODY MOVE!";
                break;
            case 8:
                speech = "THESE WERE OLD?!";
                break;
            default:
                speech = "COME BACK HERE!";
                break;
        }

        repaint();
    }

    private void resetGame() {
        if (punchTimer != null) {
            punchTimer.stop();
        }

        punching = false;
        bossReacting = false;
        punchFrame = 0;

        score = 0;
        discoveries = 0;
        selected = -1;

        Arrays.fill(discovered, false);

        ending = "YOUR FIRST OFFICE ADVENTURE";
        message = "Click an office object to discover a funny ending.";
        speech = "Where is my report?!";

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Boxing-glove button
        if (gloveBounds.contains(x, y)) {
            startPunch();
            return;
        }

        // Office objects
        for (int i = 0; i < objectBounds.length; i++) {
            if (objectBounds[i].contains(x, y)) {
                chooseObject(i);
                return;
            }
        }

        // Log ending
        if (confirmBounds.contains(x, y)) {
            if (selected >= 0) {
                message = jokes[selected] + " Ending logged!";
            } else {
                message = "Choose an object first!";
            }

            repaint();
            return;
        }

        // Replay
        if (retryBounds.contains(x, y)) {
            if (selected >= 0) {
                score += 10;
                message = jokes[selected] + " Replay bonus: +10!";
            } else {
                message = "Choose an object to replay.";
            }

            repaint();
            return;
        }

        // Reset game
        if (menuBounds.contains(x, y)) {
            int answer = JOptionPane.showConfirmDialog(
                this,
                "Start a new game? Your score will reset.",
                "New Game",
                JOptionPane.YES_NO_OPTION
            );

            if (answer == JOptionPane.YES_OPTION) {
                resetGame();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(
                    "1000 Funny Ways to Outsmart Your Boss"
                );

                BossComedyGame game = new BossComedyGame();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(game);
                frame.pack();
                frame.setMinimumSize(new Dimension(1100, 720));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}