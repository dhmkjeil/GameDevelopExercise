package com.example.game.system.pathfinder;

import android.graphics.Point;

import java.util.ArrayList;

public class Node {
    BattleMap map;
    Node previousNode;
    private int x, y;
    private float distanceFromStart;
    private float heuristicDistanceFromEnd;
    private boolean isStart;
    private boolean isEnd;
    public boolean isObstacle;

    Node(int x, int y, BattleMap map) {
        this.x = x;
        this.y = y;
        this.map = map;
        this.distanceFromStart = Integer.MAX_VALUE;
    }

    public ArrayList<Node> getNeighborList() {
        ArrayList<Node> neighborList = new ArrayList<Node>();

        if (!(y == 0)) {
            neighborList.add(map.getNode(x, (y - 1)));
        }

        if (!(y == 0) && !(x == (map.getMapWidth() - 1))) {
            neighborList.add(map.getNode(x + 1, y - 1));
        }

        if (!(x == map.getMapWidth() - 1)) {
            neighborList.add(map.getNode(x + 1, y));
        }

        if (!(x == (map.getMapWidth() - 1)) && !(y == (map.getMapHeight() - 1))) {
            neighborList.add(map.getNode(x + 1, y + 1));
        }

        if (!(y == (map.getMapHeight() - 1))) {
            neighborList.add(map.getNode(x, y + 1));
        }

        if (!(x == 0) && !(y == (map.getMapHeight() - 1))) {
            neighborList.add(map.getNode(x - 1, y + 1));
        }

        if (!(x == 0)) {
            neighborList.add(map.getNode(x - 1, y));
        }

        if (!(x == 0) && !(y == 0)) {
            neighborList.add(map.getNode(x - 1, y - 1));
        }

        return neighborList;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public boolean getIsEnd() {
        return isEnd;
    }

    public float getDistanceFromStart() {
        return distanceFromStart;
    }

    public float getHeuristicDistanceFromEnd () {
        return heuristicDistanceFromEnd;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public void setDistanceFromStart(float distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public void setHeuristicDistanceFromEnd(float heuristicDistanceFromEnd) {
        this.heuristicDistanceFromEnd = heuristicDistanceFromEnd;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
}
