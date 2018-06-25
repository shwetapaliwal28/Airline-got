
package edu.pdx.cs410J.spaliwal.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import edu.pdx.cs410J.AirportNames;
import com.google.gwt.user.client.ui.TextArea;

import java.util.Date;
import java.util.*;
import com.google.gwt.user.client.Command;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {


  public AirlineGwt() {
  }

  @Override
  public void onModuleLoad() {
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });

  }

  private void setupUI() {

    Command readMeCommand= new Command() {
      @Override
      public void execute() {
        Window.alert("Project 5: READ ME\nProject Name : A Rich Internet Application for an Airline\n" +"Course Name  : Advanced JAVA Programming\n" +
                        "Author       : Shweta Paliwal\n" +
                        "Contact     : spaliwal@pdx.edu\n"+
                "Welcome to Airport Traffic Management System.\n" +
                "This application will processes Airline and Flight information provided by user via user interface using GWT.\n ");
      }
    };

    MenuBar menuBar = new MenuBar();
    MenuBar helpMenuBar= new MenuBar();
    menuBar.setAutoOpen(true);
    menuBar.setWidth("300px");
    menuBar.addItem(new MenuItem("Help",true,helpMenuBar));
    helpMenuBar.addItem("README",readMeCommand);

    Label airlineNameLabel= new Label("Airline Name");
    Label flightNumberLabel=new Label("Flight Number");
    Label sourceCodeLabel=new Label("Source Airport");
    Label departureDateLabel=new Label("Departure Date and Time");
    Label arrivalCodeLabel=new Label("Arrival Airport");
    Label arrivalDateLabel=new Label("Arrival Date and Time");

    final TextBox airlineNameTb = new TextBox();
    airlineNameTb.setMaxLength(30);
    airlineNameTb.setVisibleLength(30);
    airlineNameTb.getElement().setAttribute("placeholder", "Eg: AirIndia");

    final TextBox flightNumberTb= createTextBoxNumbersOnly();
    flightNumberTb.setMaxLength(10);
    flightNumberTb.getElement().setAttribute("placeholder", "Eg: 123");

    final TextBox sourceCodeTb=createTextBoxLettersOnly();
    sourceCodeTb.setMaxLength(3);
    sourceCodeTb.getElement().setAttribute("placeholder", "LAS");
    sourceCodeTb.setVisibleLength(3);

    final TextBox departureDatetb= new TextBox();
    departureDatetb.getElement().setAttribute("placeholder", "mm/dd/yyyy hh:mm am");

    final TextBox arrivalCodeTb=createTextBoxLettersOnly();
    arrivalCodeTb.setMaxLength(3);
    arrivalCodeTb.getElement().setAttribute("placeholder", "PDX");
    arrivalCodeTb.setVisibleLength(3);

    final TextBox arrivalDatetb= new TextBox();
    arrivalDatetb.getElement().setAttribute("placeholder", "mm/dd/yyyy hh:mm am");

    Label airlineNameSearchLabel= new Label("Airline Name");
    Label sourceCodeSearchLabel=new Label("Source Airport");
    Label arrivalCodeSearchLabel=new Label("Arrival Airport");

    final TextBox airlineNameSearchTb = new TextBox();
    airlineNameSearchTb.setMaxLength(30);
    airlineNameSearchTb.setVisibleLength(30);
    airlineNameSearchTb.getElement().setAttribute("placeholder", "Eg: AirIndia");


    final TextBox sourceCodeSearchTb=createTextBoxLettersOnly();
    sourceCodeSearchTb.setMaxLength(3);
    sourceCodeSearchTb.getElement().setAttribute("placeholder", "LAS");
    sourceCodeSearchTb.setVisibleLength(3);

    final TextBox arrivalCodeSearchTb=createTextBoxLettersOnly();
    arrivalCodeSearchTb.setMaxLength(3);
    arrivalCodeSearchTb.getElement().setAttribute("placeholder", "PDX");
    arrivalCodeSearchTb.setVisibleLength(3);

    final TextArea prettyTextArea=new TextArea();
    prettyTextArea.setCharacterWidth(70);
    prettyTextArea.setVisibleLines(20);

    HorizontalPanel hp = new HorizontalPanel();

    HorizontalPanel hwrite = new HorizontalPanel();
    hwrite.getElement().getStyle().setPadding(20, Style.Unit.PX);


    VerticalPanel p1 = new VerticalPanel();
    p1.setSpacing(5);
    VerticalPanel pSearch = new VerticalPanel();
    pSearch.setSpacing(5);
    p1.getElement().getStyle().setPadding(20, Style.Unit.PX);

    pSearch.getElement().getStyle().setPadding(20, Style.Unit.PX);

    pSearch.getElement().getStyle().setPadding(20, Style.Unit.PX);

    RootPanel rootPanel = RootPanel.get();

    rootPanel.add(menuBar);

    Button toAddFlightBt = new Button("Add Flight");
    toAddFlightBt.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {

        String airlineName = airlineNameTb.getText();
        String flightNumber = flightNumberTb.getText();
        String src = sourceCodeTb.getText();

        String departDate = departureDatetb.getText();

        String dest = arrivalCodeTb.getText();

        String arrivalDate = arrivalDatetb.getText();
        try {
          checkEmpty(airlineName,flightNumber,src,departDate,dest,arrivalDate);
        } catch (Exception e) {
          Window.alert(e.getMessage());
          return;
        }

        int flightNo= parseFlightNumber(flightNumber);

        try{
          checkCode(src);
          checkCode(dest);
        } catch(Exception e){
          Window.alert(e.getMessage());
          return;
        }
        departDate = isValidDateRegex(departDate);

        if(departDate == null)
        {
          return;
        }
        arrivalDate = isValidDateRegex(arrivalDate);
        if(arrivalDate == null)
        {
          return;
        }
        try {
          parseDate(departDate);
          parseDate(arrivalDate);
        }catch(Exception e){
          Window.alert(e.getMessage());
          return;
        }

        Flight flight = new Flight(flightNo, src, departDate, dest, arrivalDate);

          AirlineServiceAsync async = GWT.create(AirlineService.class);
          async.addFlight(airlineName, flight, new AsyncCallback<HashMap>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert("Flight has been added to the server.");
            }

            @Override
            public void onSuccess(HashMap hashMap) {
              Window.alert("Flight has been added to the server successfully");
            }
          });
        }
      });

    Button resetBt = new Button("Reset All");
    resetBt.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        airlineNameTb.setText("");
        airlineNameTb.getElement().setAttribute("placeholder", "AirIndia");
        flightNumberTb.setText("");
        flightNumberTb.getElement().setAttribute("placeholder", "123");
        sourceCodeTb.setText("");
        sourceCodeTb.getElement().setAttribute("placeholder", "LAS");
        departureDatetb.setText("");
        departureDatetb.getElement().setAttribute("placeholder", "mm/dd/yyyy hh:mm am");
        arrivalCodeTb.setText("");
        arrivalCodeTb.getElement().setAttribute("placeholder", "PDX");
        arrivalDatetb.setText("");
        arrivalDatetb.getElement().setAttribute("placeholder", "mm/dd/yyyy hh:mm am");
        airlineNameSearchTb.setText("");
        airlineNameSearchTb.getElement().setAttribute("placeholder", "AirIndia");
        sourceCodeSearchTb.setText("");
        sourceCodeSearchTb.getElement().setAttribute("placeholder", "LAS");
        arrivalCodeSearchTb.setText("");
        arrivalCodeSearchTb.getElement().setAttribute("placeholder", "PDX");
        prettyTextArea.setText("");
      }
    });

    Button search = new Button("Search Flights");
    search.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        final String airlineName = airlineNameSearchTb.getText();
        final String src = sourceCodeSearchTb.getText().toUpperCase();
        final String dest = arrivalCodeSearchTb.getText().toUpperCase();
        if(airlineName.isEmpty() ||src.isEmpty() ||dest.isEmpty())
        {
          Window.alert("All the three fields are mandatory!!");
          return;
        }
          try{
            checkCode(src);
            checkCode(dest);
          } catch(Exception e){
            Window.alert(e.getMessage());
            return;
          }

        AirlineServiceAsync async = GWT.create(AirlineService.class);
        async.searchFlight(airlineName,src,dest,new AsyncCallback<HashMap>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.toString());

          }

          @Override
          public void onSuccess(HashMap hashMap) {
            int count=0;
            if(!hashMap.containsKey(airlineName)){
              Window.alert("No records found for Airline : "+ airlineName);
            }
            Airline a = (Airline) hashMap.get(airlineName);
            Airline airline1=new Airline(airlineName);
            Collection<Flight> flights;
            flights = a.getFlights();
            for (Flight f : flights) {
              if (f.getSource().equals(src.toUpperCase())) {
                if (f.getDestination().equals(dest.toUpperCase())) {
                  airline1.addFlight(f);
                  count+=1;
                }
              }
            }

            if(count==0) {
              Window.alert("Sorry! There are no Flights from " + src + " to " + dest+ " for airline : "+ airlineName);
            }
            else
            {
              prettyTextArea.setText(airline1.flightPrettyDump());
            }

          }
        });
      }
    });

    Button displayAll= new Button("Display All Flights");
    displayAll.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        AirlineServiceAsync async = GWT.create(AirlineService.class);
        async.displayAllFlights(new AsyncCallback<HashMap>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.toString());
          }

          @Override
          public void onSuccess(HashMap hashMap) {
            ArrayList<String> keys = new ArrayList<String>();
            for (Object key : hashMap.keySet()) {
              String n = String.valueOf(key);
              keys.add(n);
            }
            StringBuilder prettyText = new StringBuilder();
            for (String s : keys) {
              Airline a = (Airline) hashMap.get(s);
              Collection<Flight> flightList;
              flightList = a.getFlights();
              Airline airline1=new Airline(s);
              for(Flight flight1 : flightList) {
                airline1.addFlight(flight1);
              }
              prettyText.append(airline1.flightPrettyDump());
              prettyText.append("\n");
            }
            prettyTextArea.setText(prettyText.toString());
          }
        });
      }
    });


    p1.add(airlineNameLabel);
    p1.add(airlineNameTb);
    p1.add(flightNumberLabel);
    p1.add(flightNumberTb);
    p1.add(sourceCodeLabel);
    p1.add(sourceCodeTb);
    p1.add(departureDateLabel);
    p1.add(departureDatetb);
    p1.add(arrivalCodeLabel);
    p1.add(arrivalCodeTb);
    p1.add(arrivalDateLabel);
    p1.add(arrivalDatetb);
    p1.add(toAddFlightBt);
    p1.add(resetBt);


    pSearch.add(airlineNameSearchLabel);
    pSearch.add(airlineNameSearchTb);
    pSearch.add(sourceCodeSearchLabel);
    pSearch.add(sourceCodeSearchTb);
    pSearch.add(arrivalCodeSearchLabel);
    pSearch.add(arrivalCodeSearchTb);
    pSearch.add(search);
    pSearch.add(displayAll);


    hp.add(p1);
    hp.add(pSearch);

    hwrite.add(prettyTextArea);

    rootPanel.add(hp);
    rootPanel.add(hwrite);


  }

  // referred http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/TextBox.html

  private TextBox createTextBoxNumbersOnly() {
    TextBox tb = new TextBox();
    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        if (!Character.isDigit(event.getCharCode())) {
          ((TextBox) event.getSource()).cancelKey();
        }
      }
    });
    return tb;
  }

  private TextBox createTextBoxLettersOnly() {
    TextBox tb = new TextBox();
    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        if (Character.isDigit(event.getCharCode())) {
          ((TextBox) event.getSource()).cancelKey();
        }
      }
    });
    return tb;
  }

  /**
   * Validate if the fields are empty
   * @param airlineName name of airline
   * @param flightNumber flight number
   * @param src source airport code
   * @param departDate Departure date time
   * @param dest destination airport code
   * @param arrivalDate  Arrival date time
   */
  private void checkEmpty(String airlineName,String flightNumber, String src, String departDate, String dest, String arrivalDate) throws Exception{
    String str = "";
    boolean empty = false;

    if(airlineName.isEmpty()){
      str += "Airline Name  ";
      empty = true;
    }
    if(flightNumber.isEmpty()){
      str += "Flight Number  ";
      empty = true;
    }
    if(src.isEmpty()){
      str += "Source Aiport  ";
      empty = true;
    }
    if (departDate.isEmpty()) {
      str += "Departure Day   ";
      empty = true;
    }

    if (dest.isEmpty()) {
      str += "Destination Airport  ";
      empty= true;
    }
    if (arrivalDate.isEmpty()) {
      str += "Arrival Time  ";
      empty = true;
    }
    if(empty){
      throw new Exception("Please enter the listed mandatory fields below: " + "\n" + str);
    }

  }

  /**
   * Validate date time format with SimpleDateFormat class
   * @param strDate string for validation
   * @return String if valid date fromat, exits if invalid date format
   */
  private static String parseDate(String strDate) throws Exception {
    DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
    Date date = null;
    try {
      date = df.parseStrict(strDate);
    } catch (Exception e) {
      throw new Exception("Invalid date : " +strDate );
    }
    return date.toString();
  }

  /**
   * Validate source and destination three letter code.
   * @param code for validation
   * @return true a valid code, exception if code is invalid
   */

  private static String checkCode(String code) throws Exception {
    String name="";
    if (code.matches("[a-zA-Z]+") && code.length() == 3) {
      name = AirportNames.getName(code.toUpperCase());
      if (name == null) {
        throw new Exception("The code - " + code + "  does not correspond to a known airport.Please try again with valid code");
      }
    }
    else{
      throw new Exception("The code - " + code + " is invalid.Please try again with valid code");
    }
    return code;
  }

  /**
   * Validate flight number by parsing it integer type
   * @param flightNumber flight number for validation
   * @return true an integer, exception if flight number is invalid
   */
  public static Integer parseFlightNumber(String flightNumber) {
    try {
      return Integer.parseInt(flightNumber);

    } catch (NumberFormatException ex) {
      Window.alert("Invalid flight number: " + flightNumber);
      return 0;
    }
  }

  /**
   * Validate date time format with regular expression
   * @param strDate date address for validation
   * @return true valid date fromat, false invalid date format
   */

  public static String isValidDateRegex(String strDate) {

    String DATE_PATTERN = "^([0]?[1-9]|[1][0-2])/([0]?[1-9]|[1|2][0-9]|[3][0|1])/([0-9]{4})\\s*([0-9]|0[1-9]|1[0-2]):([0-5][0-9])\\s*(pm|am)$";

    if (!strDate.matches(DATE_PATTERN))
    {
      Window.alert("Invalid arrival date-time format: " +  strDate+". Please input date in format - mm/dd/yyyy hh:mm am/pm");
      return null;
    }
    return strDate;
  }

}
