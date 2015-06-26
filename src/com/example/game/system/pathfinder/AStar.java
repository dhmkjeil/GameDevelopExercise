package com.example.game.system.pathfinder;

import android.graphics.Point;

import java.util.ArrayList;

public class AStar {
    private BattleMap map;
    private DiagonalHeuristic dHeuristic;

    private ArrayList<Node> closedList;
    private ArrayList<Node> openList;

    public AStar(BattleMap map, DiagonalHeuristic dHeuristic) {
        this.map = map;
        this.dHeuristic = dHeuristic;
        closedList = new ArrayList<Node>();
        openList = new ArrayList<Node>();
    }

    public ArrayList<Point> calcShortestPath(int startX, int startY, int endX, int endY) {
        map.setStartLocation(startX, startY);
        map.setEndLocation(endX, endY);

        if (map.getNode(endX, endY).isObstacle) {
            // 장애물이 있다는 메세지
            return null;
        }

        map.getStartNode().setDistanceFromStart(0);
        listClear();
        openList.add(map.getStartNode());

        while (openList.size() != 0) {
            Node currentNode = openList.get(0);

            if (currentNode.getX() == map.getEndLocationX() && currentNode.getY() == map.getEndLocationY()) {
                return reconstructPath(currentNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            for (Node neighbor : currentNode.getNeighborList()) {
                boolean neighborIsCloser;

                if (closedList.contains(neighbor))
                    continue;

                if (!neighbor.isObstacle) {
                    float neighborDistanceFromStart = (currentNode.getDistanceFromStart() + map.getDistanceBetween(currentNode, neighbor));

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighborIsCloser = true;
                    } else  if (neighborDistanceFromStart < currentNode.getDistanceFromStart()) {
                        neighborIsCloser = true;
                    } else {
                        neighborIsCloser = false;
                    }

                    if (neighborIsCloser) {
                        neighbor.setPreviousNode(currentNode);
                        neighbor.setDistanceFromStart(neighborDistanceFromStart);
                        neighbor.setHeuristicDistanceFromEnd(dHeuristic.getDistanceToEnd(neighbor.getPoint(), map.getEndPoint()));
                    }
                }
            }
        }

        return null;
    }

    private ArrayList<Point> reconstructPath(Node node) {
        ArrayList<Point> path = new ArrayList<Point>();
        while(!(node.getPreviousNode() == null)) {
            path.add(0, node.getPoint());
            node = node.getPreviousNode();
        }
        return path;
    }

    public void listClear() {
        closedList.clear();
        openList.clear();
    }
}
