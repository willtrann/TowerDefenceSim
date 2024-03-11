package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

/**
 * The type App.
 */
public class App extends PApplet {

    /**
     * The constant CELLSIZE.
     */
    public static final int CELLSIZE = 32;
    /**
     * The constant SIDEBAR.
     */
    public static final int SIDEBAR = 120;
    /**
     * The constant TOPBAR.
     */
    public static final int TOPBAR = 40;
    /**
     * The constant BOARD_WIDTH.
     */
    public static final int BOARD_WIDTH = 20;

    /**
     * The constant WIDTH.
     */
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    /**
     * The constant HEIGHT.
     */
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    /**
     * The constant FPS.
     */
    public static final int FPS = 60;

    /**
     * The Shrub img.
     */
    PImage shrub_img, /**
     * The Path 1 img.
     */
    path1_img, /**
     * The Path 2 img.
     */
    path2_img, /**
     * The Path 3 img.
     */
    path3_img, /**
     * The Path 0 img.
     */
    path0_img, /**
     * The Wizardhouse img.
     */
    wizardhouse_img, /**
     * The Grass img.
     */
    grass_img;

    /**
     * The Config path.
     */
    public String configPath;

    /**
     * The Waves.
     */
    public List<Wave> waves;
    /**
     * The Multiplier.
     */
    public double multiplier = 1.0;
    /**
     * The Wave timer.
     */
    public double waveTimer = 0;
    /**
     * The Current wave index.
     */
    public int currentWaveIndex = 0;
    /**
     * The First run.
     */
    public boolean firstRun = true;
    /**
     * The Active monsters.
     */
    public List<Monster> activeMonsters = new ArrayList<>();
    /**
     * The Active towers.
     */
    public List<Tower> activeTowers = new ArrayList<>();
    /**
     * The Active fireballs.
     */
    public List<Fireball> activeFireballs = new ArrayList<>();
    /**
     * The Normal tower images.
     */
    public List<PImage> normalTowerImages = new ArrayList<>();
    /**
     * The Slow tower images.
     */
    public List<PImage> slowTowerImages = new ArrayList<>();

    /**
     * The Entrances.
     */
    public List<Coordinate> entrances;
    /**
     * The Fireball image.
     */
    public PImage fireballImage, /**
     * The Slowball image.
     */
    slowballImage;
    /**
     * The Grid boolean.
     */

    public boolean[][] gridBoolean = new boolean[20][20];

    /**
     * The Game mana.
     */
    public Mana gameMana;
    /**
     * The Upgrade on spot.
     */
    public List<String> upgradeOnSpot = new ArrayList<>();

    /**
     * The Icons.
     */
    public String[] icons = {"FF", "P", "T", "S","U1", "U2", "U3", "M"};
    /**
     * The Icons labels.
     */
    public String[] icons_labels = {"x2 speed", "PAUSE", "Build Tower", "Build Slow Tower","Upgrade Range", "Upgrade Speed", "Upgrade Damage", "Mana pool cost:"};

    /**
     * The Custom frame count.
     */
    public int customFrameCount = 0;
    /**
     * The Config.
     */
    public JSONObject config;
    /**
     * The Selected tower.
     */
    public Tower selectedTower;
    /**
     * The Icon selected.
     */
    public List<String> iconSelected = new ArrayList<>();
    /**
     * The Last wave.
     */
    public boolean lastWave;
    /**
     * The Last wave spawned.
     */
    public boolean lastWaveSpawned;


    /**
     * The enum Game phase.
     */
    enum GamePhase{
        /**
         * Normal game phase.
         */
        Normal,
        /**
         * Double speed game phase.
         */
        DoubleSpeed,
        /**
         * Paused game phase.
         */
        Paused,
        /**
         * Lose game phase.
         */
        Lose,
        /**
         * Win game phase.
         */
        Win;

    }

    /**
     * The enum Tower operation.
     */
    enum TowerOperation{
        /**
         * Normal tower operation.
         */
        Normal,
        /**
         * Tower placing tower operation.
         */
        TowerPlacing,
        /**
         * Slow tower placing tower operation.
         */
        SlowTowerPlacing,
        /**
         * Tower selected tower operation.
         */
        TowerSelected,
        /**
         * Tower dual operation tower operation.
         */
        TowerDualOperation;
    }

    /**
     * The Game phase.
     */
    public GamePhase gamePhase = GamePhase.Normal;
    /**
     * The Tower operation.
     */
    public TowerOperation towerOperation = TowerOperation.Normal;

    /**
     * The Normal tower cost.
     */
    int normal_tower_cost;
    /**
     * The Tower cost combination.
     */
    int tower_cost_combination = 0;
    /**
     * The Normal tower range.
     */
    int normal_tower_range;
    /**
     * The Normal firing speed.
     */
    double normal_firing_speed;
    /**
     * The Normal tower damage.
     */
    int normal_tower_damage;

    /**
     * The Slowtower cost.
     */
    int slowtower_cost;
    /**
     * The Slowtower range.
     */
    int slowtower_range;
    /**
     * The Slowtower firing speed.
     */
    double slowtower_firing_speed;
    /**
     * The Slowtower damage.
     */
    int slowtower_damage;
    /**
     * The Slowtower slow.
     */
    double slowtower_slow;

    /**
     * Load json json object.
     *
     * @param path the path
     * @return the json object
     */
    public JSONObject loadJSON(String path) {
        String[] lines = loadStrings(path);
        String jsonString = join(lines, ""); // get all the json objects from json path
        return  JSONObject.parse(jsonString);
    }

    /**
     * Get path p image.
     *
     * @param main_line  the main line
     * @param map_points the map points
     * @param i          the
     * @param j          the j
     * @param path0_img  the path 0 img
     * @param path1_img  the path 1 img
     * @param path2_img  the path 2 img
     * @param path3_img  the path 3 img
     * @return the p image
     */
    public PImage getPath(String main_line, ArrayList<String> map_points, int i, int j, PImage path0_img, PImage path1_img, PImage path2_img,PImage path3_img){
        int leftPoint, rightPoint, upPoint, downPoint;
        char leftElem, rightElem, upElem, downElem;
        leftPoint = j - 1;
        rightPoint = j + 1;
        upPoint = i - 1;
        downPoint = i + 1;//points adjacent to point

        int max_vert = map_points.size();
        int max_hori = main_line.length();


        if (0 <= leftPoint && leftPoint < max_hori) {
            leftElem = main_line.charAt(leftPoint); // if left point not empty
        }
        else{
            leftElem = ' ';
        }
        if (0 <= rightPoint && rightPoint < max_hori) {
            rightElem = main_line.charAt(rightPoint); // if right point not empty
        }
        else{
            rightElem = ' ';
        }
        if (0 <= upPoint && upPoint < max_vert) {
            upElem = map_points.get(upPoint).charAt(j); // if up point not empty
        }
        else{
            upElem = ' ';
        }

        if (0 <= downPoint && downPoint < max_vert) {
            downElem = map_points.get(downPoint).charAt(j); // if down point not empty
        }
        else{
            downElem = ' ';
        }

        boolean leftCon = leftElem == 'X'; // if theres an X adjacent to point
        boolean rightCon = rightElem == 'X';
        boolean upCon = upElem == 'X';
        boolean downCon = downElem == 'X';


        // returns an image based on boolean connections and rotates if necessary to fulfill joining

        if(leftCon && rightCon && !upCon && !downCon){
            return path0_img;
        }
        else if(!leftCon && !rightCon && (upCon || downCon)) {
            return rotateImageByDegrees(path0_img, 90);
        }
        else if(leftCon && !rightCon && !upCon && downCon) {
            return path1_img;
        }
        else if(leftCon && !rightCon && upCon && !downCon) {
            return rotateImageByDegrees(path1_img,90);
        }
        else if(!leftCon && rightCon && upCon && !downCon) {
            return rotateImageByDegrees(path1_img, 180);
        }
        else if(!leftCon && rightCon && !upCon && downCon) {
            return rotateImageByDegrees(path1_img, -90);
        }
        else if(leftCon && rightCon && !upCon && downCon) {
            return path2_img;
        }
        else if(leftCon && rightCon && upCon && !downCon) {
            return rotateImageByDegrees(path2_img, 180);
        }
        else if(leftCon && !rightCon && upCon && downCon) {
            return rotateImageByDegrees(path2_img,90);
        }
        else if(!leftCon && rightCon && upCon && downCon) {
            return rotateImageByDegrees(path2_img, 90);
        }
        else if(leftCon && rightCon && upCon && downCon) {
            return path3_img;
        }
        else{
            return path0_img;
        }
    }

    /**
     * Wizard house.
     */
    public void WizardHouse(){
        wizardhouse_img = loadImage("src/main/resources/WizardTD/wizard_house.png");
        File f = new File(config.getString("layout"));

        try {
            Scanner scan = new Scanner(f);
            ArrayList<String> map_points = new ArrayList<>(); // get map
            while (scan.hasNextLine()) {
                map_points.add(scan.nextLine());
            }

            int height = map_points.size();

            int maxWidth = 0;
            for (String line : map_points) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }
            int y = 0; // vert
            while (y < height) {
                String temp_line = map_points.get(y);
                int x = 0; // horiz
                while (x < maxWidth) {

                    char temp;
                    if (x < temp_line.length()) {
                        temp = temp_line.charAt(x); // loop through map until find Wizard house W
                    } else {
                        temp = ' ';
                    }

                    if (temp == 'W') {
                        char leftElem, rightElem, upElem, downElem;

                        int leftPoint = x - 1; // gets adjacent points to wizard house
                        int rightPoint = x + 1;
                        int upPoint = y - 1;
                        int downPoint = y + 1;

                        int max_vert = map_points.size();
                        int max_hori = temp_line.length();

                        // gets element at adjacent points

                        if (0 <= leftPoint && leftPoint < max_hori) {
                            leftElem = temp_line.charAt(leftPoint);
                        } else {
                            leftElem = ' ';
                        }
                        if (0 <= rightPoint && rightPoint < max_hori) {
                            rightElem = temp_line.charAt(rightPoint);
                        } else {
                            rightElem = ' ';
                        }
                        if (0 <= upPoint && upPoint < max_vert) {
                            upElem = map_points.get(upPoint).charAt(x);
                        } else {
                            upElem = ' ';
                        }

                        if (0 <= downPoint && downPoint < max_vert) {
                            downElem = map_points.get(downPoint).charAt(x);
                        } else {
                            downElem = ' ';
                        }

                        boolean leftCon = leftElem == 'X'; // see what X is adjacent to wizard house
                        boolean rightCon = rightElem == 'X';
                        boolean upCon = upElem == 'X';
                        boolean downCon = downElem == 'X';

                        // draws image of wizard house at point and rotates if required

                        if (rightCon) {
                            image(wizardhouse_img, x * CELLSIZE - 8, y * CELLSIZE + CELLSIZE);
                        } else if (leftCon) {
                            image(rotateImageByDegrees(wizardhouse_img, 180), x * CELLSIZE - 8, y * CELLSIZE + CELLSIZE);
                        } else if (upCon) {
                            image(rotateImageByDegrees(wizardhouse_img, -90), x * CELLSIZE - 8, y * CELLSIZE + CELLSIZE);
                        } else if (downCon) {
                            image(rotateImageByDegrees(wizardhouse_img, 90), x * CELLSIZE - 8, y * CELLSIZE + CELLSIZE);
                        }
                    }
                    x++;
                }
                y++;
            }
        }catch(FileNotFoundException e){
            System.out.println("Invalid file!");
        }
    }

    /**
     * Setup grid.
     */
    public void setupGrid(){

        File f = new File(config.getString("layout")); // get map


        try{
            Scanner scan = new Scanner(f);
            ArrayList<String> map_points= new ArrayList<>();
            while(scan.hasNextLine()){
                map_points.add(scan.nextLine());
            }

            int height = map_points.size();

            int maxWidth = 0;
            for (String line : map_points) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }

            int i = 0; // vert
            while(i < height) {
                String temp_line = map_points.get(i);
                int j = 0; // horiz
                while (j < maxWidth) {

                    char temp;
                    if (j < temp_line.length()) {
                        temp = temp_line.charAt(j);
                    } else {
                        temp = ' ';
                    }

                    if (temp == 'S' || temp == 'X' || temp == 'W') { // fills boolean grid depending if space is occupied or not
                        gridBoolean[i][j] = false;
                    }
                    else{
                        gridBoolean[i][j] = true;
                    }
                    j++;
                }
                i++;
            }
        } catch(FileNotFoundException e){
            System.err.println("File not found!");
        }
    }

    /**
     * Map.
     */
    public void Map(){

        File f = new File(config.getString("layout"));

        try{
            Scanner scan = new Scanner(f);
            ArrayList<String> map_points= new ArrayList<>(); // load map
            while(scan.hasNextLine()){
                map_points.add(scan.nextLine());
            }

            int height = map_points.size();

            int maxWidth = 0;
            for (String line : map_points) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }

            int i = 0; // vert
            while(i < height) {
                String temp_line = map_points.get(i);
                int j = 0; // horiz
                while (j < maxWidth) {

                    char temp;
                    if (j < temp_line.length()) {
                        temp = temp_line.charAt(j);
                    } else {
                        temp = ' ';
                    }

                    if (temp == 'S') {
                        image(shrub_img, j * CELLSIZE, i * CELLSIZE + TOPBAR); // image(img, horiz, vert)
                    } else if (temp == 'X') {
                        PImage path_img = getPath(temp_line, map_points, i, j, path0_img, path1_img, path2_img, path3_img);
                        image(path_img, j * CELLSIZE, i * CELLSIZE + TOPBAR); // draw map element based on map file
                    }
                    else{
                        image(grass_img, j * CELLSIZE, i * CELLSIZE + TOPBAR);
                    }
                    j++;
                }
                i++;
            }
        } catch(FileNotFoundException e){
            System.err.println("File not found!");
        }
    }

    /**
     * Monster array list list.
     *
     * @param list the list
     * @return the list
     */
    public List<Monster> monsterArrayList(JSONArray list){
        List<Monster> monsters = new ArrayList<>();
        int i = 0;

        while(i < list.size()){
            JSONObject monsterObject = list.getJSONObject(i);
            String sType = monsterObject.getString("type"); // get monster attributes from json object
            int hp = monsterObject.getInt("hp");
            float speed = monsterObject.getFloat("speed");
            double armour = monsterObject.getDouble("armour");
            int manaGainedOnKill = monsterObject.getInt("mana_gained_on_kill");
            int quantity = monsterObject.getInt("quantity");

            int j = 0;
            while(j < quantity){
                if(sType != null){ // appends monster to arraylist for wave if it isnt null
                    monsters.add(new Monster(loadMonsterImages(sType), hp, speed, armour, manaGainedOnKill));
                }
                else{
                    return null;
                }
                j ++;
            }

            i ++;
        }

        return monsters;
    }

    /**
     * Get entrances list.
     *
     * @return the list
     */
    public List<Coordinate> getEntrances(){
        List<Coordinate> entranceCoordinates = new ArrayList<>();
        File f = new File(config.getString("layout")); // get map

        try {
            Scanner scan = new Scanner(f);
            ArrayList<String> map_points = new ArrayList<>();
            while (scan.hasNextLine()) {
                map_points.add(scan.nextLine());
            }

            int maxWidth = 0;
            for (String line : map_points) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }

            //checks all boundaries of the map (i.e edges of the map) and see's if any X's are there. if so add to list of entrances

            int i = 0;
            while(i < map_points.size()){
                String line = map_points.get(i);
                if (line.charAt(0) == 'X'){
                    entranceCoordinates.add(new Coordinate(0, i));
                }
                // Check the right side of the line only if the line is as long as maxWidth
                if (line.length() == maxWidth && line.charAt(line.length() - 1) == 'X'){
                    entranceCoordinates.add(new Coordinate(maxWidth - 1, i));
                }
                i++;
            }

            int j = 0;
            String topScreenElems = map_points.get(0);
            while(j < topScreenElems.length()){
                if(topScreenElems.charAt(j) == 'X'){
                    entranceCoordinates.add(new Coordinate(j, 0));
                }
                j ++;
            }

            String bottomScreenElems = map_points.get(map_points.size() - 1);
            int k = 0;
            while(k < bottomScreenElems.length()){
                if(bottomScreenElems.charAt(k) == 'X'){
                    entranceCoordinates.add(new Coordinate(k, map_points.size() - 1));
                }
                k ++;
            }

            return entranceCoordinates;
        } catch(FileNotFoundException e){
            System.err.println("File not found!");
            return entranceCoordinates; // return empty list if file is not found
        }

    }

    /**
     * Load tower image.
     */
    public void loadTowerImage(){
        PImage tower0 = loadImage("src/main/resources/WizardTD/tower0.png");
        PImage tower1 = loadImage("src/main/resources/WizardTD/tower1.png");
        PImage tower2 = loadImage("src/main/resources/WizardTD/tower2.png"); // loading PImage normal tower
        normalTowerImages.add(tower0);
        normalTowerImages.add(tower1);
        normalTowerImages.add(tower2);
        PImage slowtower0 = loadImage("src/main/resources/WizardTD/slowtower0.png");
        PImage slowtower1 = loadImage("src/main/resources/WizardTD/slowtower1.png");
        PImage slowtower2 = loadImage("src/main/resources/WizardTD/slowtower2.png");  //loading PImage slow tower
        slowTowerImages.add(slowtower0);
        slowTowerImages.add(slowtower1);
        slowTowerImages.add(slowtower2);
    }

    /**
     * Load map images.
     */
    public void loadMapImages(){
        shrub_img = loadImage("src/main/resources/WizardTD/shrub.png");
        path0_img = loadImage("src/main/resources/WizardTD/path0.png"); // loading map element images
        path1_img = loadImage("src/main/resources/WizardTD/path1.png");
        path2_img = loadImage("src/main/resources/WizardTD/path2.png");
        path3_img = loadImage("src/main/resources/WizardTD/path3.png");
        grass_img = loadImage("src/main/resources/WizardTD/grass.png");
    }

    /**
     * Draw buttons.
     */
    public void drawButtons(){
        int x = WIDTH - SIDEBAR + 10; // x point to draw icon
        int y_offset = TOPBAR + 10;

        strokeWeight(3);
        int i = 0;
        while(i < icons.length){
            int y = y_offset + (50) * i; // get y point to draw icon

            if(iconSelected.contains(icons[i])){
                fill(255, 255, 0); // if icon has been clicked coloured yellow
            }
            else if (mouseX > x && mouseX < x + 40 && mouseY > y && mouseY < y + 40) {
                fill(206, 206, 205); // if mouse over icon, coloured grey
            } else {
                fill(132, 115, 74); // if not, colour brown
            }

            rect(x, y, 40, 40); // draw rectangle

            fill(0);
            textSize(25);

            textAlign(CENTER, CENTER);
            text(icons[i], x + 20, y + 20); // symbol text in button


            if((icons[i].equals("T") || icons[i].equals("M") || icons[i].equals("S")) && (mouseX > x && mouseX < x + 40 && mouseY > y && mouseY < y + 40)){
                fill(255, 255, 255);
                rect(x - 70, y, 50, 20); // draw rectangle at point to show cost if its T, M or S

                fill(0);
                textSize(10);

                textAlign(LEFT, CENTER);

                if(icons[i].equals("T")) {
                    text("Cost: " + normal_tower_cost, x - 67, y + 10); // show Tower price
                }
                else if(icons[i].equals("M")) {
                    text("Cost: " + gameMana.getCost(), x - 67, y + 10); // show mana upgrade price
                }
                else if(icons[i].equals("S")) {
                    text("Cost: " + slowtower_cost, x - 67, y + 10); // show slow tower price
                }
            }

            textSize(12);
            textAlign(LEFT, CENTER);

            // draws text of label next to each button

            if(icons_labels[i].split(" ").length >= 2){
                if(i == 0){
                    text(icons_labels[i], x + 45, y + 8);
                }
                else if(i == icons_labels.length - 1){
                    String[] label_split = icons_labels[i].split(" "); // splits if too long
                    text(label_split[0] + " " + label_split[1], x + 45, y + 10);
                    text(label_split[2] + " " + gameMana.getCost(), x + 45, y + 27);
                }
                else {
                    String[] label_split = icons_labels[i].split(" ", 2);
                    text(label_split[0], x + 40 + 5, y + 10); // splits if too long
                    text(label_split[1], x + 40 + 5, y + 27);
                }
            }
            else{
                text(icons_labels[i], x + 45, y + 10); // print one line if only one word
            }
            i++;
        }
        stroke(0);
        strokeWeight(1); // reset stroke
    }

    /**
     * Draw mana.
     */
    public void drawMana(){
        float filledWidth = (float) gameMana.getMana() / gameMana.getMana_cap() * 300; // get part of rectangle that has mana relative to bar

        strokeWeight(2);
        stroke(0);

        fill(255, 255, 255);
        rect(400, 10, 300, 20); // draw background of bar

        fill(0, 214, 214);
        rect(400, 10, filledWidth, 20);  // draw mana user has relative to bar

        fill(0);
        textSize(21);
        text("MANA:", 320, 19); // text of mana info
        textAlign(CENTER, CENTER);
        fill(0);
        text(gameMana.getMana() + " / " + gameMana.getMana_cap(), 550, 18);

        strokeWeight(1); // reset stroke
    }

    /**
     * Check tower selected.
     *
     * @param x the x
     * @param y the y
     */
    public void checkTowerSelected(int x, int y){
        for(Tower tower:activeTowers) { //loop through towers
            if (0 < x && x < WIDTH - SIDEBAR && TOPBAR < y && y < HEIGHT) {
                if (tower.getCoordinate().x < x && x < tower.getCoordinate().x + CELLSIZE && tower.getCoordinate().y < y && y < tower.getCoordinate().y + CELLSIZE) {
                    towerOperation = TowerOperation.TowerSelected;
                    selectedTower = tower; // if mouse clicked in bounds of tower, change tower operation state and make new selected tower
                    return;
                } else {
                    selectedTower = null; // otherwise, no tower operation
                    towerOperation = TowerOperation.Normal;
                }
            }
        }
    }

    /**
     * Text game end.
     */
    public void textGameEnd(){
        fill(0, 255, 0);
        textSize(32);
        textAlign(CENTER, CENTER); // green text of you lost
        text("YOU LOST", (width / 2) - 40, (height / 2)-130);

        fill(0, 255, 0);
        textSize(32); // green text r to restart
        textAlign(CENTER, CENTER);
        text("Press 'r' to restart", (width / 2) - 40, (height / 2)-40);
    }

    /**
     * Text game win.
     */
    public void textGameWin(){
        fill(255, 0, 255);
        textSize(32); // purple text you win
        textAlign(CENTER, CENTER);
        text("YOU WIN", (width / 2) - 40, (height / 2)-130);


    }

    /**
     * Restart game phase.
     */
    public void restartGamePhase(){
        firstRun = true;
        activeMonsters.clear();
        activeTowers.clear();
        activeFireballs.clear();
        currentWaveIndex = 0;
        gameMana.resetMana();
        iconSelected.clear();
        gamePhase = GamePhase.Normal;
        towerOperation = TowerOperation.Normal;
        customFrameCount = 0;
        waveTimer =  waves.get(currentWaveIndex).getPre_wave_pause();
        selectedTower = null;
        lastWave =  false;
        lastWaveSpawned = false;
        loadWaves();

        // restart all variables to initial variables
        // clears arraylists
        // reloads waves

    }

    /**
     * Load waves.
     */
    public void loadWaves(){
        JSONArray waveConfigs = config.getJSONArray("waves");
        waves = new ArrayList<>(); // all waves for game session

        int i = 0;
        while(i < waveConfigs.size()){
            JSONObject waveInfo = waveConfigs.getJSONObject(i); // get info and attributes of all waves
            int preWavePause = waveInfo.getInt("pre_wave_pause");
            int duration = waveInfo.getInt("duration");
            JSONArray monstersJSON = waveInfo.getJSONArray("monsters");
            List<Monster> monsters = monsterArrayList(monstersJSON); //get arraylist of all monsters in wave
            Wave wave = new Wave(preWavePause, duration, monsters);
            waves.add(wave); // add new wave object into waves arraylist to iterate over
            i ++;
        }
    }

    /**
     * Wave management.
     */
    public void waveManagement(){

        if(firstRun){
            waveTimer = waves.get(currentWaveIndex).getPre_wave_pause(); // get wave timer depending on wave 1 or other wave
            firstRun = false;
        }
        else {
            if (waveTimer <= 0 && currentWaveIndex < (waves.size() - 1)) {
                currentWaveIndex++;
                waveTimer = waves.get(currentWaveIndex).getPre_wave_pause();
            }
        }

        if(currentWaveIndex < waves.size()){

            int nextWaveNumber = currentWaveIndex + 1; // while waves are less than the number of waves required
            int secondsUntilNextWave = (int) waveTimer;

            if (!(currentWaveIndex == (waves.size() - 1) && secondsUntilNextWave < 0)) {
                fill(0);
                textSize(25); // draw upcoming wave info
                text("Wave " + nextWaveNumber + " starts: " + secondsUntilNextWave, 10, 20);
            }
            else{
                lastWave = true;
            }
        }

        if(waveTimer >=0 && currentWaveIndex > 0) {
            Wave currentWave = waves.get(currentWaveIndex - 1); // get current wave
            float framesBetweenSpawns = (float) (60 * currentWave.getDuration()) / currentWave.getSizeMonsters();

            if(customFrameCount % Math.ceil(framesBetweenSpawns) == 0){
                Monster newMonster = currentWave.spawnMonster(); // spawns monster if right duration
                if(newMonster != null) {
                    Random random = new Random();
                    int path_choice = random.nextInt(entrances.size());
                    Coordinate start = entrances.get(path_choice);// get the random entrance of map


                    newMonster.setX_Pos(start.x*CELLSIZE);
                    newMonster.setY_Pos(start.y*CELLSIZE+ TOPBAR); // set monster to start of path

                    File f = new File(config.getString("layout"));

                    newMonster.setPath(start, f); // get path monster travels under

                    activeMonsters.add(newMonster); // add to all active monsters
                }
            }
        }else{
            Wave currentWave = waves.get(currentWaveIndex); // get current wave
            float framesBetweenSpawns = (float) (60 * currentWave.getDuration()) / currentWave.getSizeMonsters();


            if(customFrameCount % Math.ceil(framesBetweenSpawns) == 0){
                Monster newMonster = currentWave.spawnMonster(); // spawns monster if right duration
                if(newMonster != null) {
                    Random random = new Random();
                    int path_choice = random.nextInt(entrances.size());

                    Coordinate start = entrances.get(path_choice);// get the random entrance of map


                    newMonster.setX_Pos(start.x*CELLSIZE);
                    newMonster.setY_Pos(start.y*CELLSIZE+ TOPBAR); // set monster to start of path

                    File f = new File(config.getString("layout"));

                    newMonster.setPath(start, f); // get path monster travels under

                    activeMonsters.add(newMonster); // add to all active monsters
                }
            }
        }
    }

    /**
     * Draw details.
     *
     * @param monster the monster
     */
    public void drawDetails(Monster monster){
        float filledWidth = (float) (monster.getHp() / monster.initial_hp * 40);

        strokeWeight(0);
        stroke(0); //draws hp bar

        fill(255, 0, 0);
        rect(monster.getX_Pos() - 10, monster.getY_Pos() - 15, 40, 5);

        fill(0, 255, 0);
        rect(monster.getX_Pos() - 10, monster.getY_Pos() - 15, filledWidth, 5);

        if(monster.slowed) {
            fill(255, 255, 0);
            textSize(12); // if slowed, show text over monster that shows slowed
            text("SLOWED", monster.getX_Pos() - 10, monster.getY_Pos() - 20);
        }
        strokeWeight(1);
    }

    /**
     * Monster management.
     */
    public void monsterManagement(){
        List<Monster> survivingMonsters = new ArrayList<>(); // to update monsters if they die

        if(lastWave && !activeMonsters.isEmpty()){
            lastWaveSpawned = true; // to determine if last wave and monsters are in last wave
        }
        for (Monster monster : activeMonsters) {
            if(gamePhase != GamePhase.Paused) {
                monster.updatePosition(); // move monster to next position
                monster.MonsterFrameCount++;

                if(gamePhase == GamePhase.DoubleSpeed){
                    monster.updatePosition(); // moves double if fast forward
                    monster.MonsterFrameCount++;
                }
            }
            image(monster.getType(), monster.getX_Pos(), monster.getY_Pos());  // draw monster

            if (!monster.getPath().isEmpty()) {
                if(monster.isDead()){
                    boolean fullDead = false; // if dead plays dying animation 4 frames per image
                    if(monster.MonsterFrameCount%4 == 0) {
                        fullDead = monster.dyingAnimation();
                    }
                    if(fullDead){
                        gameMana.increaseManaOnKill(monster.getMana_gained_on_kill()); // gain mana when fully killed monster
                    }
                    else{
                        survivingMonsters.add(monster); // add to surviving monster if not fully dead yet to keep drawing
                    }
                }
                else {
                    drawDetails(monster); // draw health bars and slowed if not dying or dead
                    survivingMonsters.add(monster); // add to existing monsters
                }
            }
            else{
                boolean gameEnd = gameMana.decreaseMana((int) monster.getHp());  // if reached wizard house, take mana damage
                monster.resetPath(); // respawns monster
                monster.slowed = false;
                survivingMonsters.add(monster);
                if(gameEnd){
                    gamePhase = GamePhase.Lose;  // if mana <= 0 lose
                }
            }

        }
        activeMonsters =  survivingMonsters;  // updates monsters
    }

    /**
     * Mana management.
     */
    public void manaManagement(){
        if(customFrameCount % (FPS/multiplier) == 0){
            gameMana.increaseMana(); // increases mana per second
        }
    }

    /**
     * Interact gui.
     *
     * @param index the index
     */
    public void interactGUI(int index) {
        iconSelected.add(icons[index]); // keep icons clicked coloured yellow


        if (icons[index].equals("M")) {
            gameMana.manaUpgrade(); // upgrade mana if clicked
        }
        if (icons[index].equals("P")) {
            if (gamePhase == GamePhase.Paused) {
                gamePhase = GamePhase.Normal;
                iconSelected.remove("P"); // unpaused if clicked
            }else {
                if(iconSelected.contains("FF")){
                    iconSelected.remove("FF");
                }
                gamePhase = GamePhase.Paused; // paused if clicked
            }
        }
        if (icons[index].equals("T")) {
            if (towerOperation == TowerOperation.TowerPlacing) {
                towerOperation = TowerOperation.Normal; // not placing normal tower anymore
            } else {
                towerOperation = TowerOperation.TowerPlacing;
                iconSelected.remove("S"); // ready to place normal tower if clicked
            }
        }

        if (icons[index].equals("S")) {
            if(towerOperation == TowerOperation.SlowTowerPlacing){
                towerOperation = TowerOperation.Normal; // not placing slow tower anymore
                iconSelected.remove("S");
            }
            else{
                towerOperation = TowerOperation.SlowTowerPlacing;
                iconSelected.remove("T"); // ready to place slow tower if clicked
            }
        }

        if (icons[index].equals("FF")) {
            if (gamePhase == GamePhase.DoubleSpeed) {
                gamePhase = GamePhase.Normal; // un fastforward if clicked
                iconSelected.remove("FF");
            } else {
                if(iconSelected.contains("P")){
                    iconSelected.remove("P");
                }
                gamePhase = GamePhase.DoubleSpeed; // fastforward if clicked
            }
        }


        if (towerOperation == TowerOperation.TowerSelected) {
            if (icons[index].equals("U1")) {
                if (gameMana.getMana() - selectedTower.cost_range > 0) {
                    gameMana.decreaseMana(selectedTower.cost_range); // upgrades selected tower range if clicked
                    selectedTower.upgradeRange();
                }
            }
            if (icons[index].equals("U2")) {
                if (gameMana.getMana() - selectedTower.cost_speed > 0) {
                    gameMana.decreaseMana(selectedTower.cost_speed);
                    selectedTower.upgradeSpeed();  // upgrades selected tower speed if clicked
                }
            }
            if (icons[index].equals("U3")) {
                if (gameMana.getMana() - selectedTower.cost_dmg > 0) {
                    gameMana.decreaseMana(selectedTower.cost_dmg);
                    selectedTower.upgradeDamage();  // upgrades selected tower damage if clicked
                }
            }
        }
        if(towerOperation == TowerOperation.TowerPlacing || towerOperation == TowerOperation.TowerDualOperation){
            if (icons[index].equals("U1")) {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("R")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10;  // upgrades tower range if clicked when the tower placed down
                    upgradeOnSpot.add("R");
                }
            }
            if (icons[index].equals("U2")) {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("S")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10; // upgrades tower speed if clicked when the tower placed down
                    upgradeOnSpot.add("S");

                }
            }
            if (icons[index].equals("U3")) {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("D")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10; // upgrades tower damage if clicked when the tower placed down
                    upgradeOnSpot.add("D");
                }
            }
        }



    }

    /**
     * Fire ball management.
     */
    public void fireBallManagement() {
        Iterator<Fireball> iterator = activeFireballs.iterator();

        while (iterator.hasNext()) {
            Fireball fireball = iterator.next(); // use iterator object to get next fireball

            if (fireball != null) {
                if (!fireball.reachedTarget()) {
                    if (gamePhase != GamePhase.Paused) {
                        fireball.moveFireball(); // if fireball hasnt reached target and not paused
                        if (fireball.reachedTarget()) {
                            processFireball(fireball, iterator); // deal deductions to target monster if hit
                            continue;
                        }
                        if (gamePhase == GamePhase.DoubleSpeed) {
                            fireball.moveFireball(); // if double speed, move twice
                            if (fireball.reachedTarget()) {
                                processFireball(fireball, iterator); // deal deductions to target monster if hit
                                continue;
                            }
                        }
                    }
                    image(fireball.getType(), fireball.getX(), fireball.getY()); // draw fireball
                } else {
                    processFireball(fireball, iterator); // deal deductions if hit
                }
            }
        }
    }

    private void processFireball(Fireball fireball, Iterator<Fireball> iterator) {
        if(fireball.getParentTower() instanceof SlowTower){
            fireball.getTarget().slowMonster(((SlowTower) fireball.getParentTower()).getSlowRate()); // if parent tower slow tower, slow target
        }
        fireball.getTarget().takeDamage(fireball.getDmg()); // damage tower
        iterator.remove(); // remove from active fireballs as it already hit object
    }


    /**
     * Animate screen.
     */
    public void animateScreen(){
        waveManagement(); // draws wave timer and manges waves
        monsterManagement(); // draws monsters and manages monsters
        fireBallManagement();  // draws fireballs and manages fireballs
        manaManagement(); // manages mana
        if(gamePhase != GamePhase.Paused) {
            customFrameCount++; // keeps frame going if not paused
        }
    }

    /**
     * Icon interaction.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     */
    public void iconInteraction(int mouseX, int mouseY){
        int x = WIDTH - SIDEBAR + 10; // 650
        int y_offset = TOPBAR + 10; //50

        int i = 0;

        while(i < icons.length){
            int boxY = y_offset + (50) * i; //50, 100, 150, 200, 250, 300, 350

            if ((mouseX > x && mouseX < x + 40) && (mouseY > boxY && mouseY < boxY + 40)) {

                interactGUI(i); // if mouse clicked inside these bounds, interact with buttons and find out what button does
            }
            i++;
        }
    }

    /**
     * Draw towers.
     */
    public void drawTowers(){
        for (Tower tower : activeTowers) {
            tower.towerImage(); // get tower image updated if required

            if(tower.towerFrameCount % Math.floor((FPS/multiplier)/tower.getTower_firing_speed()) == 0) {
                if(gamePhase != GamePhase.Paused) {
                    Fireball newFireball = tower.shootFireball(activeMonsters); // if tower duration reached, fire a new fireball and add to active fireballs
                    if (newFireball != null && !activeFireballs.contains(newFireball)) {
                        activeFireballs.add(newFireball);
                    }
                }
            }

            image(tower.getType(), tower.getCoordinate().x, tower.getCoordinate().y);
            drawUpgrades(tower); // draw all user upgrades on towers
            tower.towerFrameCount++; // upgrades tower frame count
        }
    }

    /**
     * Tower placement.
     *
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     */
    public void towerPlacement(int mouseX, int mouseY){
        int end_x = WIDTH - SIDEBAR;

        if(0 < mouseX && mouseX < end_x && TOPBAR < mouseY && mouseY < HEIGHT) {
            int x = mouseX/CELLSIZE; // get mouse grid coordinate for grid
            int y = (mouseY -TOPBAR)/CELLSIZE;

            if(gridBoolean[y][x] && (gameMana.getMana() - normal_tower_cost > 0)){ // if grid unoccupied
                Tower tower;
                int CoordX = x * CELLSIZE;
                int CoordY = y * CELLSIZE + TOPBAR;  // place new tower based on which cell user clicked
                Coordinate coordinate = new Coordinate(CoordX, CoordY);
                if(towerOperation == TowerOperation.SlowTowerPlacing){  // places slow tower if placing slow tower
                    tower = new SlowTower(slowTowerImages, coordinate, slowtower_range, slowtower_firing_speed, slowtower_damage, slowballImage, slowtower_slow);
                }
                else{ // places normal tower if placing slow tower
                    tower = new Tower(normalTowerImages, coordinate, normal_tower_range, normal_firing_speed, normal_tower_damage, fireballImage);
                }

                // upgrades on spot if done before placing down
                if(upgradeOnSpot.contains("D")){
                    tower.upgradeDamage();
                }
                if(upgradeOnSpot.contains("R")){
                    tower.upgradeRange();
                }
                if(upgradeOnSpot.contains("S")){
                    tower.upgradeSpeed();
                }

                //clear upgrades for next tower
                // add to active towers for drawing
                // update mana
                // occupy grid now tower placed
                upgradeOnSpot.clear();
                activeTowers.add(tower);
                gridBoolean[y][x] = false;
                gameMana.decreaseMana(normal_tower_cost);
                normal_tower_cost = 100; // reset tower cost from upgrades
            }
        }
        iconSelected.remove("T");

    }

    /**
     * Load tower variables.
     */
    public void loadTowerVariables(){
        normal_tower_cost = config.getInt("tower_cost");
        normal_tower_range = config.getInt("initial_tower_range"); // load variables from config
        normal_firing_speed = config.getDouble("initial_tower_firing_speed");
        normal_tower_damage = config.getInt("initial_tower_damage");

        slowtower_cost = config.getInt("slowtower_cost");
        slowtower_range = config.getInt("initial_slowtower_range");
        slowtower_firing_speed = config.getDouble("initial_slowtower_firing_speed");
        slowtower_damage = config.getInt("initial_slowtower_damage");
        slowtower_slow = config.getDouble("slowtower_slow");
    }

    /**
     * Load mana.
     */
    public void loadMana(){
        int initial_mana = config.getInt("initial_mana");
        int initial_cap = config.getInt("initial_mana_cap"); // load mana variables from config and makes mana object
        double initial_rate = config.getDouble("initial_mana_gained_per_second");
        int initial_cost = config.getInt("mana_pool_spell_initial_cost");
        int costincrease = config.getInt("mana_pool_spell_cost_increase_per_use");
        double capincrease = config.getDouble("mana_pool_spell_cap_multiplier");
        double rateincrease = config.getDouble("mana_pool_spell_mana_gained_multiplier");
        gameMana = new Mana(initial_cost, initial_rate, initial_mana, initial_cap, costincrease,capincrease,rateincrease);
    }

    /**
     * Draw selected tower.
     *
     * @param tower the tower
     */
    public void drawSelectedTower(Tower tower){
        int centerX = (int) tower.getCoordinate().x + 16;
        int centerY = (int) tower.getCoordinate().y + 16;

        stroke(255, 255, 0); // draws circular range of selected tower
        noFill();

        ellipse(centerX, centerY, tower.getTower_range() * 2, tower.getTower_range() * 2);



        // displays upgrade menu costs of selected tower
        stroke(0);
        fill(255, 255, 255);

        rect(650, 550, 100, 30);

        fill(0);
        textSize(12);
        text("Upgrade Cost", 700, 565);

        stroke(0);
        fill(255, 255, 255);

        rect(650, 580, 100, 50);

        fill(0);
        textSize(12);
        if(tower.damage_level == tower.range_level && tower.range_level == tower.speed_level){
            text("Damage: " + tower.cost_dmg, 700, 590);
            text("Speed: " + tower.cost_speed, 700, 605);
            text("Range: " + tower.cost_range, 700, 620);
            tower_cost_combination += tower.cost_dmg + tower.cost_speed + tower.cost_range;
        }
        else {
            if (tower.damage_level < tower.range_level || tower.damage_level < tower.speed_level) {
                text("Damage: " + tower.cost_dmg, 700, 590);
                tower_cost_combination += tower.cost_dmg;
            }
            if (tower.speed_level < tower.range_level || tower.speed_level < tower.damage_level) {
                text("Speed: " + tower.cost_speed, 700, 605);
                tower_cost_combination += tower.cost_speed;
            }
            if (tower.range_level < tower.speed_level || tower.range_level < tower.damage_level) {
                text("Range: " + tower.cost_range, 700, 620);
                tower_cost_combination += tower.cost_range;
            }
        }

        stroke(0);
        fill(255, 255, 255);

        rect(650, 630, 100, 30);

        fill(0);
        textSize(12);
        text("Total: " + tower_cost_combination, 700, 645);

        stroke(0);
        strokeWeight(1);
        tower_cost_combination = 0;
    }

    /**
     * Draw upgrades.
     *
     * @param tower the tower
     */
    public void drawUpgrades(Tower tower){
        if (!(tower.speed_level == tower.range_level && tower.range_level == tower.damage_level) || (tower.speed_level > 2 && tower.range_level > 2 && tower.damage_level > 2)){
            fill(255, 0, 255);
            textSize(9);
            for (int i = 0; i < tower.range_level; i++) {
                text("O", tower.getCoordinate().x + i * 5, tower.getCoordinate().y + 5); // draw O's for each level of range
            }
            stroke(0);
            strokeWeight(1);
            noFill();
            stroke(0, 100, 200);
            for (int i = 0; i < tower.speed_level; i++) {
                strokeWeight(1 + i);
                float squareX = tower.getCoordinate().x + CELLSIZE / 4;
                float squareY = tower.getCoordinate().y + CELLSIZE / 4;
                rect(squareX, squareY, CELLSIZE / 2, CELLSIZE / 2); // draw square for each speed level and thickens
            }
            stroke(0);
            strokeWeight(1);
            for (int i = 0; i < tower.damage_level; i++) {
                text("X", tower.getCoordinate().x + i * 5, tower.getCoordinate().y + App.CELLSIZE); // draw X's for each level of damage
            }
            stroke(0);
            strokeWeight(1);
        }
    }

    /**
     * Load balls.
     */
    public void loadBalls(){
        fireballImage = loadImage("src/main/resources/WizardTD/fireball.png");
        slowballImage = loadImage("src/main/resources/WizardTD/slowball.png"); // load fireball images
    }

    /**
     * Load monster images list.
     *
     * @param s the s
     * @return the list
     */
    public List<PImage> loadMonsterImages(String s){
        List<PImage> gremlinImgs = new ArrayList<>();
        List<PImage> beetleImgs = new ArrayList<>();
        List<PImage> wormImgs = new ArrayList<>();
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin.png"));
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin1.png"));
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin2.png"));
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin3.png")); // load images of monsters
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin4.png"));
        gremlinImgs.add(loadImage("src/main/resources/WizardTD/gremlin5.png"));
        beetleImgs.add(loadImage("src/main/resources/WizardTD/beetle.png"));
        wormImgs.add(loadImage("src/main/resources/WizardTD/worm.png"));

        if(s.equals("gremlin")){
            return gremlinImgs;
        }
        else if(s.equals("worm")){
            return wormImgs; // return each PImage list based on whatever config asks for
        }
        else if(s.equals("beetle")){
            return beetleImgs;
        }
        else{
            return null;
        }

    }

    /**
     * Instantiates a new App.
     */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
        size(760, 680);

        config = loadJSON(this.configPath);
        entrances = getEntrances();
        setupGrid();
        loadTowerImage();
        loadMapImages();
        loadTowerVariables();
        loadMana();
        loadBalls();
        loadWaves(); // load all variables for game

    }


    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        if (key == 'r' || key == 'R' && gamePhase == GamePhase.Lose) {
            restartGamePhase(); // if loses and user pressed 'R', restarts game
        }

        if (key == 'M' || key == 'm') {
            gameMana.manaUpgrade();
            iconSelected.add("M"); // upgrade mana if 'M' pressed
        }

        if (key == 'P' || key == 'p') {
            if(gamePhase == GamePhase.Paused){
                gamePhase = GamePhase.Normal; // unpauses if 'P' pressed
                iconSelected.remove("P");
            }
            else if(gamePhase != GamePhase.Lose){
                gamePhase = GamePhase.Paused;
                iconSelected.add("P"); // pauses if 'P' pressed
                iconSelected.remove("FF");
            }
        }

        if (key == 'F' || key == 'f') {
            if(gamePhase == GamePhase.DoubleSpeed){
                gamePhase = GamePhase.Normal; // un fastforwards if 'F' pressed
                iconSelected.remove("FF");
            }
            else if(gamePhase != GamePhase.Lose){
                gamePhase = GamePhase.DoubleSpeed;
                iconSelected.add("FF"); // fastforwards if 'F' pressed
                iconSelected.remove("P");
            }
        }

        if (key == 'T' || key == 't') {
            if(towerOperation == TowerOperation.TowerPlacing){
                towerOperation = TowerOperation.Normal; // if already placing tower, stop this operation when 'T' pressed
                iconSelected.remove("T");
            }
            else{
                towerOperation = TowerOperation.TowerPlacing;
                iconSelected.add("T"); //  set operation to placing tower when 'T' pressed
                iconSelected.remove("S");

            }
        }
        if (key == 'S' || key == 's') {
            if(towerOperation == TowerOperation.SlowTowerPlacing){
                towerOperation = TowerOperation.Normal; // if already placing slow tower, stop this operation when 'S' pressed
                iconSelected.remove("S");
            }
            else{
                towerOperation = TowerOperation.SlowTowerPlacing;
                iconSelected.add("S"); // set operation placing slow tower when 'S' pressed
                iconSelected.remove("T");
            }
        }
        if(towerOperation == TowerOperation.TowerSelected){
            if (key == '1') {
                if(gameMana.getMana() - selectedTower.cost_range > 0){
                    gameMana.decreaseMana(selectedTower.cost_range); // upgrade range of selected tower if '1' Pressed
                    selectedTower.upgradeRange();
                }
                iconSelected.add("U1");
            }
            if (key == '2') {
                if(gameMana.getMana() - selectedTower.cost_speed > 0){
                    gameMana.decreaseMana(selectedTower.cost_speed); // upgrade speed of selected tower if '2' Pressed
                    selectedTower.upgradeSpeed();
                }
                iconSelected.add("U2");
            }
            if (key == '3') {
                if(gameMana.getMana() - selectedTower.cost_dmg > 0){
                    gameMana.decreaseMana(selectedTower.cost_dmg); // upgrade damage of selected tower if '3' Pressed
                    selectedTower.upgradeDamage();
                }
                iconSelected.add("U3");
            }
        }
        if(towerOperation == TowerOperation.TowerPlacing || towerOperation == TowerOperation.TowerDualOperation){
            if (key == '1') {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("R")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10; // upgrade range of upcoming tower if '1' Pressed
                    upgradeOnSpot.add("R");
                }
                iconSelected.add("U1");
            }
            if (key == '2') {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("S")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10; // upgrade speed of upcoming tower if '2' Pressed
                    upgradeOnSpot.add("S");

                }
                iconSelected.add("U2");
            }
            if (key == '3') {
                if(gameMana.getMana() - (normal_tower_cost + 10) > 0 && !upgradeOnSpot.contains("D")){
                    towerOperation = TowerOperation.TowerDualOperation;
                    normal_tower_cost += 10; // upgrade damage of upcoming tower if '3' Pressed
                    upgradeOnSpot.add("D");
                }
                iconSelected.add("U3");
            }
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY(); // get x and y coordinates of mouse click
        if(towerOperation == TowerOperation.TowerPlacing || towerOperation == TowerOperation.TowerDualOperation || towerOperation == TowerOperation.SlowTowerPlacing) {
            towerPlacement(mouseX, mouseY); // handles tower placement depending where mouse pressed
        }
        checkTowerSelected(mouseX, mouseY); // check if user clicked a tower

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY(); // get x and y coordinates of mouse click

        iconInteraction(mouseX, mouseY); // see which icon mouse clicked

    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {


        if(gamePhase == GamePhase.Lose){
            iconSelected.clear(); // if game lose, stop game completely
            textGameEnd(); // display text
            return;
        }

        if(gamePhase == GamePhase.Win){
            iconSelected.clear(); // if game win, stop game completely
            textGameWin(); // display text
            return;
        }

        if(gamePhase == GamePhase.DoubleSpeed){
            multiplier = 2.0; // changes multiplier to double when fast forwarded
        }
        else{
            multiplier = 1.0;
        }

        if(gamePhase == GamePhase.Normal && towerOperation == TowerOperation.Normal){
            iconSelected.clear(); // clear all icons selected if nothing happening
        }

        background(132, 115, 74); // colour background
        Map(); //draw map
        if(selectedTower != null){
            drawSelectedTower(selectedTower); // if selected a tower, draw its range feature and upgrade cost menu
            if((selectedTower.damage_level == selectedTower.range_level && selectedTower.range_level == selectedTower.speed_level) || (iconSelected.contains("U3") && iconSelected.contains("U2") && iconSelected.contains("U1"))){
                iconSelected.remove("U3");
                iconSelected.remove("U2");
                iconSelected.remove("U1");
            }
        }

        drawTowers(); // draw other towers
        drawButtons(); // draw buttons



        animateScreen(); // draw all animated parts of map like monsters, fireballs

        WizardHouse(); // draws wizard house
        drawMana(); // draw mana bar

        if(gamePhase != GamePhase.Paused) {
            waveTimer -= (1.0 / FPS)*multiplier; // updates wave timer if not paused
        }

        if(activeMonsters.isEmpty() && lastWaveSpawned){ // if last wave and killed all monsters in that wave
            iconSelected.clear(); // remove all icons selected
            gamePhase = GamePhase.Win; // win game
        }


    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     *
     * @param pimg  The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
