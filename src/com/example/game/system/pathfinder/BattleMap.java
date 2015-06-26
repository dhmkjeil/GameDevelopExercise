package com.example.game.system.pathfinder;

import android.graphics.Point;

import java.util.ArrayList;

public class BattleMap {

    private int mapWidth;
    private int mapHeight;
    private ArrayList<ArrayList<Node>> map;
    private int startLocationX = 0;
    private int startLocationY = 0;
    private int endLocationX = 0;
    private int endLocationY = 0;

    public BattleMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        createMap();
    }

    private void createMap() {
        Node node;
        map = new ArrayList<ArrayList<Node>>();
        for (int x = 0; x < mapWidth; x++) {
            map.add(new ArrayList<Node>());
            for (int y = 0; y < mapHeight; y++) {
                node = new Node(x, y, this);
                map.get(x).add(node);
            }
        }
    }

    public Node getNode(int x, int y) {
        return map.get(x).get(y);
    }

    public Node getStartNode() {
        return map.get(startLocationX).get(startLocationY);
    }

    public Node getEndNode() {
        return map.get(endLocationX).get(endLocationY);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getStartLocationX() {
        return startLocationX;
    }

    public int getStartLocationY() {
        return startLocationY;
    }

    public int getEndLocationX() {
        return endLocationX;
    }

    public int getEndLocationY() {
        return endLocationY;
    }

    public Point getEndPoint() {
        return new Point(endLocationX, endLocationY);
    }

    public float getDistanceBetween(Node node1, Node node2) {
        if (node1.getX() == node2.getX() || node1.getY() == node2.getY()) {
            return 1;
        } else {
            return 1.9f;
        }
    }

    public void setStartLocation(int x, int y) {
        map.get(startLocationX).get(startLocationY).setIsStart(false);
        map.get(x).get(y).setIsStart(true);
        startLocationX = x;
        startLocationY = y;
    }

    public void setEndLocation(int x, int y) {
        map.get(endLocationX).get(endLocationY).setIsEnd(false);
        map.get(x).get(y).setIsEnd(true);
        endLocationX = x;
        endLocationY = y;
    }
}
