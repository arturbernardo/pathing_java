package org.pathing;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class DemoPanel extends JPanel {

    int step = 0;
    final int maxCol = 15;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;

    public DemoPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        setStartNode(3,6);
        setGoalNode(11, 3);

        setSolidNode(10,2);
        setSolidNode(10,3);
        setSolidNode(10,4);
        setSolidNode(10,5);
        setSolidNode(10,6);
        setSolidNode(10,7);
        setSolidNode(6,1);
        setSolidNode(6,2);
        setSolidNode(7,2);
        setSolidNode(8,2);
        setSolidNode(9,2);
        setSolidNode(11,7);
        setSolidNode(12,7);

        setCostOnNodes();
    }
    private void setStartNode(int col, int row) {
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    private void setSolidNode(int col, int row) {
        node[col][row].setAsSolid();
    }

    private void setCostOnNodes() {
        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow) {
            getCost(node[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node) {

        // G COST (start to node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H COST (goal to node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F COST
        node.fCost = node.gCost + node.hCost;

        // Display cost in the node
        if (node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost);
        }
    }

    public void autoSearch() {
        while (goalReached == false && step < 300) {
            search();
            step++;
        }
    }

    public void search() {
        if (goalReached == false) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            // open up node
            if (row - 1 >= 0)
                openNode(node[col][row-1]);

            // open left node
            if (col - 1 >= 0)
                openNode(node[col-1][row]);

            // open down node
            if (row + 1 < maxRow)
                openNode(node[col][row+1]);

            // open right node
            if (col + 1 < maxCol)
                openNode(node[col+1][row]);

            // find best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // check if F cost is better
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                // if F cost is equal, check G cost
                else if (openList.get(i).fCost == bestNodeFCost){
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }

                // get the best node
                currentNode = openList.get(bestNodeIndex);

                if (currentNode == goalNode) {
                    goalReached = true;
                    trackThePath();
                }
            }
        }
    }

    private void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        Node current = goalNode;

        while(current != startNode) {
            current = current.parent;

            if (current != startNode) {
                current.setAsPath();
            }
        }
    }

//    public void restart() {
//        step = 0;
//
//        currentNode = startNode;
//        openList = new ArrayList<>();
//        checkedList = new ArrayList<>();
//
//        goalReached = false;
//    }
}
