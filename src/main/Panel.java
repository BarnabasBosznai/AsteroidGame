package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.io.*;


/**
 * A képernyőket jelenítni meg.
 * Állapotgépnek tekinthető.
 * Az átmenetekkor intézi a jelenlegi menü, hogy a következő megfelelően induljon.
 * Mindig új ablakot hoz létre a külön menüknek.
 */
public class Panel {

    /**
     * Ebben tárolom el a játékos adatait.
     */
    private Profile profile;

    /**
     * Tárolja, hogy az aktuális játékos milyen állapotú.
     * 0, ha nincs.
     * 1, ha van neve, de nincsen félbehagyott és mentett játéka.
     * 2, ha van mind neve, mind félbehagyott játéka.
     */
    private int player_state;

    /**
     * Tárolja, hogy a melyik menü az aktuális.
     * 0, ha a főmenü.
     * 1, ha a profil módosítása van.
     * 2, ha a pályaválasztás van.
     * 3, ha a játék megy épp.
     * 4, ha a nyert a játékos.
     * 5, ha a szerkesztő.
     */
    private int menu_state;

    /**
     * Tárolja az aktuális játékot, vagy szerkesztést.
     */
    private Game game;

    /**
     * Tárolja az egér helyét(x).
     */
    private int mouse_x;

    /**
     * Tárolja az egér helyét(y).
     */
    private int mouse_y;

    /**
     * Tárolja a szerkesztésnél, hogy el van-e fordítva az objektum, vagy sem.
     */
    private boolean rotated;

    /**
     * Tárolja, hogy a szerkesztőben épp a célt akarjuk-e megadni, vagy sem.
     */
    private boolean target_select;

    /**
     * Tárolja a szerkesztőben az aktuális blokkot, amit jobb felülről behúzhatunk.
     */
    private Block selected;

    /**
     * Konstuktora a Menu osztálynak.
     * Megvizgsálja, hogy már játszott-e valaki a játékkal.
     * Ezt a "last_game.txt" fájl megléte adja meg.
     * Ebből a fájlból beolvassa a nevét a játékosnak(profilnak).
     * Majd a "neve_profil.txt"-ből kiolvassa a játékos állását / adatait.
     * Ha létezik félbehagyott játéka, akkor azt nyugtázza.
     * Végül a főmenüt állapotába állítja.
     *
     * Ha még nem játszottak ezzel a játékkal, akkor egy "üres" profilt tölt be.
     */
    public Panel(){

    /**
     * Kirazjolja az aktuális menüt.
     */
    public void draw_Menu(JFrame f) {
        switch (menu_state) {
            case (0) -> draw_Main_Menu(f);
            case (1) -> draw_Name_change(f);
            case (2) -> draw_Levels(f);
            case (3) -> draw_Game(f);
            case (4) -> draw_Victory(f);
            case (5) -> draw_Editor(f);
        }
    }

    /**
     * Kirajzola a főmenüt.
     * Beállítja az ablak nevét "Főmenü"-re.
     * Hozzáad egy mezőt, amiben kiírja a profil nevét. (nem módosítható)
     * Hozzáad 4 gombot, amivel át lehet lépni a többi menübe.
     * A pályákhoz, a profil módosításához, a fébehagyott játék folytatásához, és a szerkesztőhöz.
     * A pályákat csak akkor lehet elérni, ha van neve a játékosnak.
     * A folytatás meg akkor, ha van mentése a játékosnak.
     * A pályák gombbnál csak átállíja a menü állapotát.
     * Ugyan így tesz a név módosításánál is, ahogy a következőkben is.
     * A játék folytatásánál beolfassa fájlból a félbehagyott játékot.
     * A szerkesztésnél alaphelyzetbe állítja a szerkesztőt.
     */
    private void draw_Main_Menu(JFrame f){
        f.setTitle("Főmenü");
        Font font = new Font("serif", Font.PLAIN, 20);
        final JTextField tf=new JTextField(profile.getName());
        tf.setBounds(90,30, 120,50);
        tf.setEnabled(false);
        tf.setFont(font);
        tf.setForeground(Color.BLACK);

        Insets ins = new Insets(0,0,0,0);
        JButton b1=new JButton("Pályák");
        b1.setMargin(ins); b1.setBackground(Color.orange); b1.setFont(font);
        b1.setForeground(Color.BLACK);
        b1.setEnabled(false);
        if (player_state>=1)
            b1.setEnabled(true);
        b1.setBounds(20,100,120,60);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=2;
            }
        });

        JButton b2=new JButton("Folytatás");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font);
        b2.setForeground(Color.BLACK);
        b2.setEnabled(false);
        if (player_state==2)
            b2.setEnabled(true);
        b2.setBounds(160,100,120,60);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    FileInputStream f1 = new FileInputStream(profile.getName()+"_save.txt");
                    ObjectInputStream in = new ObjectInputStream(f1);
                    //game = new Game(-1, -1);
                    game = (Game) in.readObject();
                    in.close();
                } catch (IOException ex) {
                    System.err.println("Nem található a fájl.");
                    System.exit(-1);
                } catch (ClassNotFoundException ex) {
                    System.err.println("Kritikus hiba! Nem létezik az osztály???");
                    System.exit(-11);
                }
                menu_state=3;
            }
        });

        JButton b3=new JButton("Profil váltás");
        b3.setMargin(ins); b3.setBackground(Color.orange); b3.setFont(font);
        b3.setForeground(Color.BLACK);
        b3.setBounds(20,170,120,60);
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=1;
            }
        });

        JButton b4=new JButton("Szerkesztő");
        b4.setMargin(ins); b4.setBackground(Color.orange); b4.setFont(font);
        b4.setForeground(Color.BLACK);
        b4.setBounds(160,170,120,60);
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=5;
                game = new Game(-1, -1);
                selected = new Car(-100,-100,false);
                rotated=false;
                target_select=false;
            }
        });

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new java.awt.Color(124,82,67));
                g.fillRect(0, 0, 300, 300);
            }
        };


        f.add(b1); f.add(b2); f.add(b3); f.add(b4);
        f.add(tf);
        f.add(panel);
        f.setResizable(false);
        f.setSize(316, 300);

        f.setVisible(true);

    }

    /**
     * Kirajzolja a név módosításának menüjét.
     * Beállítja az ablak nevét "Profil módosítás"-ra.
     * Egy mezőben fogad egy Stringet.
     * Ezt el lehet fogadni, és akkor betölti a hozzátartozó profilt, ha van.
     * És mégsem gombbal vissza lehet lépni a főmenübe.
     */
    private void draw_Name_change(JFrame f){
        f.setTitle("Profil módosítás");
        Font font1 = new Font("serif", Font.PLAIN, 20);
        final JTextField tf=new JTextField(profile.getName());
        tf.setBounds(20,80, 200,40);
        tf.setEnabled(true);
        tf.setFont(font1);
        tf.setForeground(Color.BLACK);

        Insets ins = new Insets(0,0,0,0);
        JButton b1=new JButton("OK");
        b1.setMargin(ins); b1.setBackground(Color.orange); b1.setFont(font1);
        b1.setForeground(Color.BLACK);
        b1.setBounds(230,80,40,40);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
                if (tf.getText().length()!=0) {
                    System.out.println(tf.getText()+" "+"alma"+" ");
                    profile = new Profile(tf.getText());
                    if (profile.have_save())
                        player_state = 2;
                    else
                        player_state = 1;
                    save_profile();
                }
            }
        });

        JButton b2=new JButton("Mégse");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font1);
        b2.setForeground(Color.BLACK);
        b2.setBounds(110,180,80,40);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
            }
        });

        f.add(b1); f.add(b2); //f.add(b3); f.add(b4);
        f.add(tf);
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new java.awt.Color(124,82,67));
                g.fillRect(0, 0, 300, 300);
            }
        };
        f.add(panel);
        f.setResizable(false);
        f.setSize(316, 300);

        f.setVisible(true);


    }

    /**
     * Kirajzolja a játékos által elérhető pályákat.
     * Ezt a profile-ból olvassa ki.
     * Minden elérhető pályának létrehoz egy gombot.
     * Ha teljesítenek egy pályát, akkor a következő menyílik, a megoldottnak meg a színe megváltozik.
     * Gombok megnyomása esetén beolvassa fájlból a pályákat, és átállítja a menü állapotát.
     */
    private void draw_available_levels(JFrame f){
        int tutorial_state = profile.getTutorial_state();
        int easy_state = profile.getEasy_state();
        Font font1 = new Font("serif", Font.PLAIN, 20);
        Insets ins = new Insets(0,0,0,0);

        Vector<JButton> buttons_tutorial = new Vector<>();
        Vector<JButton> buttons_easy = new Vector<>();

        for (int i=0; i<5; i++){
            if (i<=tutorial_state){
                buttons_tutorial.add(new JButton((i+1)+""));
                buttons_tutorial.get(i).setMargin(ins);
                if (tutorial_state==i)
                    buttons_tutorial.get(i).setBackground(new java.awt.Color(32,230,200));
                else
                    buttons_tutorial.get(i).setBackground(new java.awt.Color(50,160,220));
                buttons_tutorial.get(i).setFont(font1);
                buttons_tutorial.get(i).setForeground(Color.BLACK);
                buttons_tutorial.get(i).setBounds(20+i*55,70,45,45);
                int finalI1 = i;
                buttons_tutorial.get(i).addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        menu_state=3;
                        try {
                            FileInputStream f1 = new FileInputStream("0_"+ finalI1 +"_level.txt");
                            ObjectInputStream in = new ObjectInputStream(f1);
                            game = (Game) in.readObject();
                            in.close();
                        } catch (IOException ex) {
                            System.err.println("Nem található a fájl.");
                            System.exit(-1);
                        } catch (ClassNotFoundException ex) {
                            System.err.println("Kritikus hiba! Nem létezik az osztály???");
                            System.exit(-11);
                        }
                    }
                });
            }
            if (i<=easy_state){
                JButton temp = new JButton((i+1)+"");
                temp.setMargin(ins);
                if (easy_state==i)
                    temp.setBackground(new java.awt.Color(200,140,200));
                else
                    temp.setBackground(new java.awt.Color(230,60,150));
                temp.setFont(font1);
                temp.setForeground(Color.BLACK);
                temp.setBounds(20+i*55,140,45,45);
                int finalI1 = i;
                temp.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        menu_state=3;
                        try {
                            FileInputStream f1 = new FileInputStream("1_"+ finalI1 +"_level.txt");
                            ObjectInputStream in = new ObjectInputStream(f1);
                            game = (Game) in.readObject();
                            in.close();
                        } catch (IOException ex) {
                            System.err.println("Nem található a fájl.");
                            System.exit(-1);
                        } catch (ClassNotFoundException ex) {
                            System.err.println("Kritikus hiba! Nem létezik az osztály???");
                            System.exit(-11);
                        }
                    }
                });
                buttons_easy.add(temp);
            }


        }

        for (JButton jButton : buttons_tutorial) f.add(jButton);

        for (JButton jButton : buttons_easy) f.add(jButton);

    }

    /**
     * Kirajzolja a játékos által elérhető pályákat.
     * (ezt a profile-ből olvassa ki, a draw_available_levels() függvénnyel)
     * Szerkesztett pályáknak a nevét megadva innen lehet elindítani a játkot.
     * (fájlból beolvasás)
     * Továbbá vissza lehet lépni a főmenübe.
     */
    private void draw_Levels(JFrame f){
        f.setTitle("Pályák");
        Font font1 = new Font("serif", Font.PLAIN, 20);
        Font font2 = new Font("serif", Font.PLAIN, 15);
        Insets ins = new Insets(0,0,0,0);
        final JTextField tf=new JTextField("Szerkesztett pálya neve...");
        tf.setBounds(20,10, 200,30);
        tf.setEnabled(true);
        tf.setFont(font2);
        tf.setForeground(Color.BLACK);


        JButton b1=new JButton("Játék");
        b1.setMargin(ins); b1.setBackground(Color.orange); b1.setFont(font1);
        b1.setForeground(Color.BLACK);
        b1.setBounds(230,10,60,30);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (tf.getText().length()!=0) {
                    File f = new File(tf.getText()+"_sandbox.txt");
                    if (f.exists()){
                        menu_state=3;
                        try {
                            FileInputStream f1 = new FileInputStream(tf.getText()+"_sandbox.txt");
                            ObjectInputStream in = new ObjectInputStream(f1);
                            game = (Game) in.readObject();
                            in.close();
                        } catch (IOException ex) {
                            System.err.println("Nem található a fájl.");
                            System.exit(-1);
                        } catch (ClassNotFoundException ex) {
                            System.err.println("Kritikus hiba! Nem létezik az osztály???");
                            System.exit(-11);
                        }
                    } else {
                        tf.setText("Nincs ilyen nevű pálya!");
                    }
                }
            }
        });

        JButton b2=new JButton("Vissza");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font1);
        b2.setForeground(Color.BLACK);
        b2.setBounds(200,210,80,40);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
            }
        });

        f.add(b1); f.add(b2);
        f.add(tf);

        draw_available_levels(f);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new java.awt.Color(124,82,67));
                g.fillRect(0, 0, 300, 300);
            }
        };
        f.add(panel);

        f.setResizable(false);
        f.setSize(316, 300);

        f.setVisible(true);

    }

    /**
     * Beállítja az ablak nevét "Játék"-ra.
     * Megjeleníti magát a játékot.
     * Kirajzolja a paint()-ben a játék területét.
     * És két gombot, egyikkel el lehet menteni a játékot.
     * Másikkal ki lehet lépni.
     * Továbbá itt figyeli az egérhasználatot az ablak.
     */
    private void draw_Game(JFrame f){
        f.setTitle("Játék");
        Font font1 = new Font("serif", Font.PLAIN, 20);
        Insets ins = new Insets(0,0,0,0);

        JButton b1=new JButton("Mentés");
        b1.setMargin(ins); b1.setBackground(Color.orange); b1.setFont(font1);
        b1.setForeground(Color.BLACK);
        b1.setEnabled(game.saveable());
        b1.setBounds(512,410,80,30);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.setIds(0,0);
                player_state=2;
                System.out.println("Mentve!");
                try {
                    FileOutputStream f = new FileOutputStream(profile.getName()+"_save.txt");
                    ObjectOutputStream out = new ObjectOutputStream(f);
                    out.writeObject(game);
                    out.close();
                } catch (IOException ex) {
                    System.err.println("Nem írható a fájl vagy a mappa.");
                    System.exit(-2);
                }

            }
        });

        JButton b2=new JButton("Kilépés");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font1);
        b2.setForeground(Color.BLACK);
        b2.setBounds(512,460,80,30);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
            }
        });

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Toolkit t=Toolkit.getDefaultToolkit();
                Image i=t.getImage("BG.png");
                g.drawImage(i, 0,0,this);
                game.draw_Game(g);

                b1.setEnabled(game.saveable());
            }
        };
        f.add(panel);
        f.add(b1); f.add(b2);

        f.add(panel);
        f.setResizable(false);
        f.setSize(504+(504-465)+73, 504+(504-465));
        f.setPreferredSize(new Dimension(504,600));
        f.setMinimumSize(new Dimension(600,504));

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouse_x=e.getX();
                mouse_y=e.getY();
                game.Blocks_move(mouse_x,mouse_y,false);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouse_x=e.getX();
                mouse_y=e.getY();
            }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                if (mouse_x>0 && mouse_y>0 && mouse_x<504 && mouse_y<504 && !target_select)
                    game.Block_pick(mouse_x,mouse_y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                game.Block_depick();
                if (game.is_Won())
                    menu_state=4;
            }

            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });

        f.setVisible(true);
    }

    /**
     * Kirajzol egy "Nyertél!" feliratot.
     * És egy gombbal vissza lehet térni a főmenübe.
     * Visszatéréskor frissíti a profilt, hogy teljesítette a pályát.
     */
    private void draw_Victory(JFrame f){
        f.setTitle("Vége");
        Font font1 = new Font("serif", Font.PLAIN, 20);
        Font font2 = new Font("serif", Font.PLAIN, 50);
        Insets ins = new Insets(0,0,0,0);

        JButton b2=new JButton("Vissza a főmenübe");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font1);
        b2.setForeground(Color.BLACK);
        b2.setBounds(60,160,160,40);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
                int stg=game.getStage();
                int lvl=game.getLevel();
                profile.update_state(stg,lvl);
            }
        });

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,0,300,300);
                g.setColor(Color.green);
                g.setFont(font2);
                g.drawString("Nyertél!",60,100);
            }
        };
        f.add(panel); f.add(b2);
        f.add(panel);
        f.setResizable(false);
        f.setSize(300, 300);

        f.setVisible(true);
    }

    /**
     * Kirajzolja a szerkesztőt.
     * Ez egy játéktáblából áll, ahová lehet pakolgatni a blokkokat.
     * Továbbá jobboldat több lehetőség közül kell választani.
     * Lehet az aktuális blokkot behúzi és elhelyezni.
     * Lehet az aktuális elfordítani.
     * Lehet az aktuális lecserélni a következőre.
     * Lehet letenni a célmezőt.
     * El lehet menteni, a megadott néven a pályát a mentés gombbal.
     * És egy kilépés gombbal ki lehet lépni a főmenöbe.
     */
    private void draw_Editor(JFrame f){

        f.setTitle("Szerkesztő");
        Font font1 = new Font("serif", Font.PLAIN, 20);
        Font font2 = new Font("serif", Font.PLAIN, 15);
        Insets ins = new Insets(0,0,0,0);
        final JTextField tf=new JTextField("Pálya neve...");
        tf.setBounds(512,370, 80,30);
        tf.setEnabled(true);
        tf.setFont(font2);
        tf.setForeground(Color.BLACK);


        JButton b1=new JButton("Mentés");
        b1.setMargin(ins); b1.setBackground(Color.orange); b1.setFont(font1);
        b1.setForeground(Color.BLACK);
        b1.setEnabled(game.saveable());
        b1.setBounds(512,410,80,30);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (tf.getText().length()!=0 && !tf.getText().equals("Pálya neve...")) {
                    // RENDES PÁLYÁK LÉTREHOZÁSA ITTENI MANUÁLS ÁTÍRÁSSAL LEHETSÉGES
                    game.setIds(-1,-1);
                    System.out.println("Mentve!");
                    try {
                        FileOutputStream f = new FileOutputStream(tf.getText()+"_sandbox.txt");
                        ObjectOutputStream out = new ObjectOutputStream(f);
                        out.writeObject(game);
                        out.close();
                    } catch (IOException ex) {
                        System.err.println("Nem írható a fájl vagy a mappa.");
                        System.exit(-2);
                    }
                }
            }
        });

        JButton b2=new JButton("Kilépés");
        b2.setMargin(ins); b2.setBackground(Color.orange); b2.setFont(font1);
        b2.setForeground(Color.BLACK);
        b2.setBounds(512,460,80,30);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu_state=0;
                rotated=false;
                target_select=false;
            }
        });



        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new java.awt.Color(100,100,100));
                g.fillRect(504, 0, 100, 504);
                g.setColor(new java.awt.Color(200,200,200));
                g.fillRect(508, 6, 88, 88);
                g.setColor(new java.awt.Color(150,150,150));
                g.fillRect(511, 9, 82, 82);

                Toolkit t=Toolkit.getDefaultToolkit();
                Image i=t.getImage("BG.png");
                g.drawImage(i, 0,0,this);
                i=t.getImage("Rotate.png");
                g.drawImage(i, 557,100,this);
                i=t.getImage("Next.png");
                g.drawImage(i, 510,100,this);                     // !!! HASZNOS !!!
                i=t.getImage("Target_button.png");
                g.drawImage(i, 510,150,this);
                g.setColor(new java.awt.Color(0,200,20));
                if (target_select)
                    g.drawRect(508,148,89,42);
                selected.drawIcon(g,rotated);
                game.draw_Game(g);

                b1.setEnabled(game.saveable());
            }
        };
        f.add(panel);
        f.add(b1); f.add(b2); //f.add(b3); f.add(b4);
        f.add(tf);
        f.add(panel);
        f.setResizable(false);
        f.setSize(504+(504-465)+73, 504+(504-465));
        f.setPreferredSize(new Dimension(504,600));
        f.setMinimumSize(new Dimension(600,504));

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouse_x=e.getX();
                mouse_y=e.getY();
                game.Blocks_move(mouse_x,mouse_y,true);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouse_x=e.getX();
                mouse_y=e.getY();
            }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("rotated");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (mouse_x>=557 && mouse_y>=103 && mouse_x<597 && mouse_y<138) {
                    rotated = !rotated;
                } else if (mouse_x>=503 && mouse_y>=103 && mouse_x<538 && mouse_y<138){
                    next_Block(selected);
                    System.out.println(selected.getName());
                } else if (mouse_x>=525 && mouse_y>=25 && mouse_x<578 && mouse_y<73){
                    game.add_Block(selected,mouse_x,mouse_y);
                    refresh_Block(selected);
                } else if (mouse_x>=513 && mouse_y>=153 && mouse_x<597 && mouse_y<187){
                    target_select=!target_select;
                } else if (mouse_x>0 && mouse_y>0 && mouse_x<504 && mouse_y<504 && !target_select){
                    game.Block_pick(mouse_x,mouse_y);
                } else if (mouse_x > 0 && mouse_y > 0 && mouse_x < 504 && mouse_y < 504){
                    game.setTarget_co(mouse_x,mouse_y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                game.Block_depick();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        f.setVisible(true);
    }

    /**
     * Visszaadja a menü állapotát.
     */
    public int getState() {return menu_state;}

    /**
     * Elmenti a játékos adatait.
     */
    public void save_profile() {
        if (profile.save_profile_state()) {
            try {
                FileOutputStream f = new FileOutputStream("last_game.txt");
                ObjectOutputStream out = new ObjectOutputStream(f);
                out.writeObject(profile.getName());
                out.close();
            } catch (IOException ex) {
                System.err.println("Nem írható a fájl vagy a mappa.");
                System.exit(-2);
            }
        }
    }

}

