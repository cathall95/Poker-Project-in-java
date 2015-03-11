import java.text.*;
import javax.swing.JOptionPane;
public class FinalVersionOfPokerProject
{
  public static String userMessage = "";  
  public static double totalWinnings = 0.0;
  public static void main(String[] args)
  {
	final int handSize = 5;
	int winType;
    int[] cards   = new int[handSize];  
    int[] suits   = new int[handSize]; 
    int[] values  = new int[handSize]; 
	Object[] menuItems1 = {"Play", "Quit"};
    Object selectedValue1 = JOptionPane.showInputDialog(null,"Choose one", 
							 "Input", 1, null, menuItems1, menuItems1[0]);
    boolean continuePlaying = true;
	if (selectedValue1 == null || selectedValue1.equals("Quit"))
	  continuePlaying = false;
	else if (selectedValue1.equals("Play"))
	{
	  while (continuePlaying)
	  {
	    generateUniqueHand(cards);
	    determineSuitsAndValues(cards, suits, values);
	    orderValuesInDescendingSequence(suits, values);
	    displayCardsToEndUser(suits, values);			
        winType = evaluateHandOfCards(suits, values);
        displayTypeOfWinIfAny(winType);       
        displayAmountWonIfAnything(winType);
        JOptionPane.showMessageDialog(null,userMessage);
		Object[] menuItems2 = {"Yes", "No"};
        Object selectedValue2 = JOptionPane.showInputDialog(null,"Choose one", 
							 "Play Again?", 1, null, menuItems2, menuItems2[0]);
	    if (selectedValue2 == null || selectedValue2.equals("No"))
	      continuePlaying = false;
	    else if(selectedValue2.equals("Yes"))
		{
	      continuePlaying = true;
		  userMessage = "";
		}
	  }  
	}
  }	
  
  public static void generateUniqueHand(int [] cards)
  {
	int deckSize = 52;
	int uniqueNumbersRequired = cards.length, aRandomNumber;
	int index = 0, duplicateIndex;
    while (index < uniqueNumbersRequired)
    {
	  aRandomNumber = (int) (Math.random() * deckSize);
	  cards[index] = aRandomNumber;
      duplicateIndex = 0;
      while (cards[duplicateIndex] != aRandomNumber)
        duplicateIndex++;
      if (index == duplicateIndex)
        index++;
    } 
  }
  
  public static void determineSuitsAndValues(int [] cards, int [] suits, 
											 int [] values)
  {
    for (int i = 0; i < cards.length; i++)
	{
	  suits[i]  = cards[i] / 13;
	  values[i] = cards[i] % 13;
    }
  }	
  
  public static void orderValuesInDescendingSequence(int [] suits, int [] values)
  {
    int pass, comparison, temp;
	boolean sorted = false;
	for (pass = 1; pass <= values.length - 1 && !sorted; pass++)
	{
	  sorted = true;
	  for (comparison = 1; comparison <= values.length - pass; comparison++)
	  {
	    if (values[comparison - 1] < values[comparison])
	    {
	      temp = values[comparison - 1];
	      values[comparison - 1] = values[comparison];
	      values[comparison] = temp;
		  temp = suits[comparison - 1];
	      suits[comparison - 1] = suits[comparison];
	      suits[comparison] = temp;
	      sorted = false;
        }  
      }
    }
  }

  public static void displayCardsToEndUser(int[] suits, int[] values)
  {
	for (int i = 0; i < suits.length; i++)
	{
	  switch(values[i])
	  {
		case 0:  userMessage += "Two of ";   break;
	    case 1:  userMessage += "Three of "; break;
	    case 2:  userMessage += "Four of ";  break;
	    case 3:  userMessage += "Five of ";  break;
	    case 4:  userMessage += "Six of ";   break;
	    case 5:  userMessage += "Seven of "; break;
	    case 6:  userMessage += "Eight of "; break;
	    case 7:  userMessage += "Nine of ";  break;
	    case 8:  userMessage += "Ten of ";   break;
	    case 9:  userMessage += "Jack of ";  break;
        case 10: userMessage += "Queen of "; break;
        case 11: userMessage += "King of ";  break;
        case 12: userMessage += "Ace of ";   break;
      } 
      switch(suits[i])
	  {
		case 0:  userMessage += "Clubs\n";    break;
	    case 1:  userMessage += "Diamonds\n"; break;
	    case 2:  userMessage += "Hearts\n";   break;
	    case 3:  userMessage += "Spades\n";   break;
      } 
    } 
  }   
  
  public static int evaluateHandOfCards(int[] suits, int[] values)
  {
    int winType = 0;  
    if (cardsOfSameSuit(suits))
    {
      if (cardsInConsecutiveDescendingSequence(values))
      {
        if (values[0] == 12) winType = 9;
        else                 winType = 8;
      }
      else                   winType = 7;
    }
    else
    {
      if (cardsInConsecutiveDescendingSequence(values)) 
	    winType = 5;
      else 
	    winType = checkOtherPossibleCombinations(values);  
    }	 
    return winType;
  }
  
  public static boolean cardsOfSameSuit(int suits[])
  {
    boolean sameSuit = true;
    for (int i = 0; (i < suits.length - 1) && sameSuit; i++)
      if (suits[i] != suits[i + 1])
        sameSuit = false;
    return sameSuit;
  } 

  public static boolean cardsInConsecutiveDescendingSequence(int values[])
  {
    boolean consecutiveCards = true;
    for (int i = 0; i < values.length - 1 && consecutiveCards; i++)
      if (values[i] != values[i + 1] + 1)
        consecutiveCards = false;
    return consecutiveCards;
  } 
  
  public static int checkOtherPossibleCombinations(int[] values)
  {
    boolean continueCardComparison;
    int sameKind = 0;
    for (int i = 0; (i < values.length - 1); i++)
    {
      continueCardComparison = true;
      for (int j = i + 1; j < values.length && continueCardComparison; j++)
      {
        if (values[i] == values[j])
          sameKind++;
        else
          continueCardComparison = false;
      } 
    } 
    return sameKind;
  } 
  
  public static void displayTypeOfWinIfAny(int winType)
  {
	switch(winType)
	{
      case 0: userMessage += "\nNot a winning hand\n"; break;
      case 1: userMessage += "\nOne pair\n";           break;
      case 2: userMessage += "\nTwo pair\n";           break;
      case 3: userMessage += "\nThree of a kind\n";    break;
      case 4: userMessage += "\nFull house\n";         break;
      case 5: userMessage += "\nStraight\n";           break;
      case 6: userMessage += "\nFour of a kind\n";     break;
      case 7: userMessage += "\nFlush\n";              break;
      case 8: userMessage += "\nStraight flush\n";     break;
      case 9: userMessage += "\nRoyal flush\n";        break;
    } 
  } 
  
  public static void displayAmountWonIfAnything(int winType)
  {
	double[] money = {0.0, 0.1, 0.2, 0.3, 0.6, 0.4, 0.7, 0.5, 0.8, 0.9};
	NumberFormat aFormatter = NumberFormat.getCurrencyInstance();
	userMessage += "\nFor this hand you win: " + 
					aFormatter.format(money[winType]);
    totalWinnings += money[winType];
    userMessage += "\nTotal winnings to date are: " + 
					aFormatter.format(totalWinnings);
  }
} 
