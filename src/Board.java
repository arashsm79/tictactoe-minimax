
public class Board {

    public static Turn turn = Turn.PLAYER1;
    
    public static boolean isPlayingWithComputer = false;

    public static int totalButtonsFilled = 0;

    public static Turn checkForWin(TicButton[][] buttons, int GridSize)
    {
        int count = 0;
        //check vertical
        for(int i = 0; i < GridSize; i++)
        {
            for(int j = 0; j < GridSize - 1; j++)
            {

                if(buttons[i][j].getState() != TicButton.States.EMPTY && buttons[i][j].getState() == buttons[i][j + 1].getState())
                {
                    count++;

                }else
                {
                    count = 0;
                }
                if(count == GridSize - 1)
                {

                    //returns the winner
                    if(buttons[i][j].getState() == TicButton.States.X)
                    {
                        return Board.Turn.PLAYER1;
                        
                    }else 
                    {
                        return Board.Turn.PLAYER2;
                    }
                }

            }
            count = 0;
        }
        

        //check horizontal
        count = 0;
        //check vertical
        for(int i = 0; i < GridSize; i++)
        {
            for(int j = 0; j < GridSize - 1; j++)
            {

                if(buttons[j][i].getState() != TicButton.States.EMPTY && buttons[j][i].getState() == buttons[j + 1][i].getState())
                {
                    count++;

                }else
                {
                    count = 0;
                }
                if(count == GridSize - 1)
                {

                    //returns the winner
                    if(buttons[j][i].getState() == TicButton.States.X)
                    {
                        return Board.Turn.PLAYER1;
                    }else 
                    {
                        return Board.Turn.PLAYER2;
                    }
                }

            }
            count = 0;

        }


        count = 0;
        //check diagonally top left to bottom right
        for(int i = 0; i < GridSize - 1; i++)
        {
            
                if(buttons[i][i].getState() != TicButton.States.EMPTY && buttons[i][i].getState() == buttons[i + 1][i + 1].getState())
                {
                    count++;

                }else
                {
                    count = 0;
                }
                if(count == GridSize - 1)
                {

                    //returns the winner
                    if(buttons[i][i].getState() == TicButton.States.X)
                    {
                        return Board.Turn.PLAYER1;
                    }else 
                    {
                        return Board.Turn.PLAYER2;
                    }
                }

            
        }

        //check diagonally top right to bottom left
        for(int i = 0; i < GridSize - 1; i++)
        {
            
                if(buttons[i][GridSize - 1 - i].getState() != TicButton.States.EMPTY && buttons[i][GridSize - 1 - i].getState() == buttons[i + 1][GridSize - 1 - i - 1].getState())
                {
                    count++;

                }else
                {
                    count = 0;
                }
                if(count == GridSize - 1)
                {

                    //returns the winner
                    if(buttons[i][GridSize - 1 - i].getState() == TicButton.States.X)
                    {
                        return Board.Turn.PLAYER1;
                    }else 
                    {
                        return Board.Turn.PLAYER2;
                    }
                }

            
        }
        

        if(totalButtonsFilled == App.GRID_SIZE * App.GRID_SIZE)
        {
            return Turn.TIE;
        }else
        {
            return null;
        }
    }

    public enum Turn {
        PLAYER1(-1),
        PLAYER2(1),
        TIE(0);

        public final int score;
        private Turn(int score)
        {
            this.score = score;
        }
        /**
         * @return the score
         */
        public int getScore() {
            return score;
        }
    }
}