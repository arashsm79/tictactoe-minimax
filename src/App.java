//Developed By Arash Sal Moslehian
//Tic Tac Toe!


// Uses Minimax (an Artificial Intelligence algorithm)
// which makes it quiet unbeatable

// GUI is implemented!



/*
You'll need javafx to compile and run this program.
download it from openjfx official site and extract the contents in somewhere like ~/Documents/libs
then set the following enviroment variable
export PATH_TO_FX=~/Documents/libs/javafx-sdk-11.0.2/lib
compile and run using the below commands
java --module-path $PATH_TO_FX --add-modules javafx.controls App
javac --module-path $PATH_TO_FX --add-modules javafx.controls App.java



Cheers!


all in one command:
export PATH_TO_FX=/where/you/put/javafx && javac --module-path $PATH_TO_FX --add-modules javafx.controls App.java && java --module-path $PATH_TO_FX --add-modules javafx.controls App

*/
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

public class App extends Application {
    
    public static final int GRID_SIZE = 3;
    @Override
    public void start(Stage stage) {
        
        //Initialize our grid
        GridPane root = new GridPane();

        //setup our button array
        TicButton[][] buttons = new TicButton[GRID_SIZE][GRID_SIZE];

        //Dialog that checks whether the players wanst to play with the computer or not
        //checks whether the player has chosen to play with commputer or not
        if(choosePlayerOrComputer(stage) == Board.Turn.PLAYER2)
        {
            whoWantsToStart(stage);
            computer(stage, root, buttons);
        }else
        {
            multiplayer(stage, root, buttons);
        }
        
        //setup the scene
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    //sets up the buttons for Multiplayer 
    public static void multiplayer(Stage stage, GridPane root, TicButton[][] buttons)
    {
        for(int col = 0; col < GRID_SIZE; col++)
        {
            for(int row = 0; row < GRID_SIZE; row++)
            { 
                buttons[col][row] = new TicButton("");
                TicButton  currentBtn = buttons[col][row];
                currentBtn.setOnAction(new EventHandler<ActionEvent>(){
                
                    @Override
                    public void handle(ActionEvent arg0) {
                        
                        if(Board.turn == Board.Turn.PLAYER1)
                        {
                            currentBtn.setText("X");
                            currentBtn.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #3421c2; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
                            currentBtn.setState(TicButton.States.X);
                            currentBtn.setDisable(true);
                            Board.turn = Board.Turn.PLAYER2;
                        }else if(Board.turn == Board.Turn.PLAYER2)
                        {
                            currentBtn.setText("O");
                            currentBtn.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #c2215f; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

                            currentBtn.setState(TicButton.States.O);
                            currentBtn.setDisable(true);
                            Board.turn = Board.Turn.PLAYER1;

                        }
                        Board.totalButtonsFilled += 1;
                        Board.Turn winner = Board.checkForWin(buttons, GRID_SIZE);
                        if(winner != null)
                        {
                            resultPopup(winner, stage);
                        }
                        
                    }
                });
                //Styling
                currentBtn.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
                currentBtn.setPrefSize(100, 100);
                root.add(currentBtn, col, row);
            }

        }
    }

    //sets up buttons for playing againts AI
    public static void computer(Stage stage, GridPane root, TicButton[][] buttons)
    {
        for(int col = 0; col < GRID_SIZE; col++)
        {
            for(int row = 0; row < GRID_SIZE; row++)
            { 
                buttons[col][row] = new TicButton("");
                TicButton  currentBtn = buttons[col][row];
                currentBtn.setOnAction(new EventHandler<ActionEvent>(){
                
                    @Override
                    public void handle(ActionEvent arg0) {
                        
                        
                            currentBtn.setText("X");
                            currentBtn.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #3421c2; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
                            currentBtn.setState(TicButton.States.X);
                            currentBtn.setDisable(true);
                            Board.totalButtonsFilled += 1;

                            //check for winner
                            Board.Turn winner = Board.checkForWin(buttons, GRID_SIZE);
                            
                            //computers turn
                            if(winner == null) //no one has won
                            { 
                                // Picks the best possible move using the Minimax algorithm
                                // which makes it unbeatable!
                                Point nextComputerMove = findTheBestPoint(buttons);

                                //Pick a random move just incase the minimax failed (it never fails but just incase :D)
                                if(nextComputerMove == null)
                                {
                                    nextComputerMove = randomMove(getAvailableMoves(buttons));
                                }


                                TicButton computerButton = buttons[nextComputerMove.getX()][nextComputerMove.getY()];
                                computerButton.setText("O");
                                computerButton.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #c2215f; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
                                computerButton.setState(TicButton.States.O);
                                computerButton.setDisable(true);
                                Board.totalButtonsFilled += 1;
                                winner = Board.checkForWin(buttons, GRID_SIZE);                            
                            }
                            
                            if(winner != null)
                            {
                                //popup dialoge showing the winner
                                resultPopup(winner, stage);

                            }

                            

                        
                       
                    }
                });

                //Styling
                currentBtn.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

                currentBtn.setPrefSize(100, 100);
                root.add(currentBtn, col, row);
            }

        }

        //if the computer is to play first, the top left corner is the optimnal place to begin
        //this part is executed once
        if(Board.isPlayingWithComputer)
        {
            Point nextComputerMove = findTheBestPoint(buttons);

            //Pick a random move just incase the minimax failed (it never fails but just incase :D)
            if(nextComputerMove == null)
            {
                nextComputerMove = randomMove(getAvailableMoves(buttons));
            }


            TicButton computerButton = buttons[nextComputerMove.getX()][nextComputerMove.getY()];
            computerButton.setText("O");
            computerButton.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #c2215f; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
            computerButton.setState(TicButton.States.O);
            computerButton.setDisable(true);
            Board.totalButtonsFilled += 1;
        }

    }


    // these two of functions below pick a random spot. just incase the AI fails (it never does but just incase ...)
    public static ArrayList<Point> getAvailableMoves(TicButton[][] buttons)
    {
        ArrayList<Point> points = new ArrayList<>();
        for(int col = 0; col < GRID_SIZE; col++)
        {
            for(int row = 0; row < GRID_SIZE; row++)
            { 
                if(buttons[col][row].getState() == TicButton.States.EMPTY)
                {
                    points.add(new Point(col, row));
                }
            }
        }
        return points;
    }
    public static Point randomMove(ArrayList<Point> availablePoints)
    {
        Collections.shuffle(availablePoints);

        return availablePoints.get(0);
    }



    //setup dialog 
    public static Board.Turn choosePlayerOrComputer(Stage stage)
    {
        Board.Turn[] chosenOption = new Board.Turn[1];
        final Stage dialog = new Stage();
        dialog.setTitle("Play againts: ");

                dialog.initModality(Modality.WINDOW_MODAL);
                //dialog.initOwner(stage);
                GridPane dialogVbox = new GridPane();
                Button btn1 = new Button("Computer");
                btn1.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #17142c; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
                //computerButton.setStyle("-fx-font-size: 6em; -fx-font-weight: bold; -fx-opacity: 1; -fx-text-fill: #c2215f;");

                Button btn2 = new Button("Multiplayer");
                btn2.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #2c141d; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

                btn1.setPrefSize(400,100);
                btn2.setPrefSize(400,100);

                btn1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        chosenOption[0] = Board.Turn.PLAYER2; //compuer
                        dialog.close();

                    }
                });

                btn2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        chosenOption[0] = Board.Turn.PLAYER1; //multiplayer
                        dialog.close();
                    }
                    
                });
                btn1.setAlignment(Pos.CENTER);
                btn2.setAlignment(Pos.CENTER);
                dialogVbox.add(btn2, 0, 0);
                dialogVbox.add(btn1, 0, 1);
                Scene dialogScene = new Scene(dialogVbox, 400, 200);
                dialog.setScene(dialogScene);
                dialog.showAndWait();

        return chosenOption[0];
        
    }

    //setup dialog number 2
    public static void whoWantsToStart(Stage stage)
    {
        final Stage dialog = new Stage();
        dialog.setTitle("Who starts?");
                dialog.initModality(Modality.WINDOW_MODAL);
                //dialog.initOwner(stage);
                GridPane dialogVbox = new GridPane();
                Button btn1 = new Button("Artificial Intelligence");
                btn1.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #17142c; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

                Button btn2 = new Button("Me");
                btn2.setStyle("-fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #2c141d; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

                btn1.setPrefSize(400,100);
                btn2.setPrefSize(400,100);

                btn1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        Board.isPlayingWithComputer = true;
                        dialog.close();

                    }
                });

                btn2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        Board.isPlayingWithComputer = false;
                        dialog.close();
                    }
                    
                });
                dialogVbox.add(btn2, 0, 1);
                dialogVbox.add(btn1, 0, 0);
                Scene dialogScene = new Scene(dialogVbox, 400, 200);
                dialog.setScene(dialogScene);
                dialog.showAndWait();

        
    }

    //result popup dialog
    public static void resultPopup(Board.Turn winner, Stage stage)
    {
        if(winner != null)
        { 
            String labelStr = "";
            if(winner == Board.Turn.PLAYER1)
            {
                labelStr = "Player X won!";
            }else if (winner == Board.Turn.PLAYER2) 
            {
                if(Board.isPlayingWithComputer)
                {

                    labelStr = "Computer won!";
                }else {
                    
                    labelStr = "Player O won!";
                }
            
            }else 
            {
                labelStr = "No one won!";
            }
            Button lb = new Button(labelStr);
            lb.setDisable(true);
            lb.setAlignment(Pos.CENTER);

            lb.setStyle("-fx-font-size: 2em; -fx-background-color:white;-fx-font-weight: bold;-fx-opacity: 1; -fx-text-fill: #17142c; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");

            Button quitBtn = new Button("Close");
            quitBtn.setStyle("-fx-font-size: 1em; -fx-font-weight: bold; -fx-text-fill: #17142c; -fx-focus-color: transparent; -fx-background-insets: 0, 0, 1, 2;");
            quitBtn.setPrefSize(400, 100);
            lb.setPrefSize(400, 100);
            quitBtn.setAlignment(Pos.CENTER);
            quitBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
        
                    stage.close();
                }
            });

            final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(stage);
                    dialog.setTitle("Winner winner! Chicken Dinner!");
                    GridPane dialogVbox = new GridPane();
                    dialogVbox.add(lb, 0, 0);
                    dialogVbox.add(quitBtn, 0, 1);
                    Scene dialogScene = new Scene(dialogVbox, 400, 100);
                    dialog.setScene(dialogScene);
                    dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent arg0) {
                            // TODO Auto-generated method stub
                            stage.close();
                            
                        }
                    });
                    dialog.showAndWait();
        }
    }
////////////////////////////////////////////////////////
    //the heart of the minimax algorithm
    /////////// MINIMAX //////////////
     public static Point findTheBestPoint(TicButton[][] buttons)
     {
         int bestScore = Integer.MIN_VALUE; //-infinity
         Point move = null;
         for(int i = 0; i < GRID_SIZE; i++)
         {
             for(int j = 0; j < GRID_SIZE; j++)
             {
                 
                 if(buttons[i][j].getState() == TicButton.States.EMPTY)
                 {
                     buttons[i][j].setState(TicButton.States.O);
                     Board.totalButtonsFilled++;

                     int score = minimax(buttons, 0, false);
                     
                     buttons[i][j].setState(TicButton.States.EMPTY);
                     Board.totalButtonsFilled--;
                     if(score > bestScore)
                     {
                         bestScore = score;
                         move = new Point(i, j);
                     }


                 }
             }
         }
         return move;
     }


     public static int minimax(TicButton[][] buttons, int depth, boolean isMaximizing)
     {
         Board.Turn result = Board.checkForWin(buttons, GRID_SIZE);
         //base condition
         if(result != null)
         {
            return result.getScore();
         }

        if(isMaximizing)
        {
            int bestScore = Integer.MIN_VALUE; //-infinity
            for(int i = 0; i < GRID_SIZE; i++)
            {
                for(int j = 0; j < GRID_SIZE; j++)
                {
                    if(buttons[i][j].getState() == TicButton.States.EMPTY)
                    {
                        buttons[i][j].setState(TicButton.States.O); //AI
                        Board.totalButtonsFilled++;
                        int score = minimax(buttons, depth + 1, false);

                        buttons[i][j].setState(TicButton.States.EMPTY);
                        Board.totalButtonsFilled--;

                        if(score > bestScore)
                        {
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;

        }else
        {
            int bestScore = Integer.MAX_VALUE; // +infinity
            for(int i = 0; i < GRID_SIZE; i++)
            {
                for(int j = 0; j < GRID_SIZE; j++)
                {
                    if(buttons[i][j].getState() == TicButton.States.EMPTY)
                    {
                        buttons[i][j].setState(TicButton.States.X); //Player
                        Board.totalButtonsFilled++;

                        int score = minimax(buttons, depth + 1, true);
                        buttons[i][j].setState(TicButton.States.EMPTY);
                        Board.totalButtonsFilled--;

                        if(score < bestScore)
                        {
                            bestScore = score;
                        }
                    }
                }
            }

            return bestScore;
        }

     }
}






