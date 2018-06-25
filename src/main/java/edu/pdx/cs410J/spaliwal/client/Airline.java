package edu.pdx.cs410J.spaliwal.client;

import edu.pdx.cs410J.AbstractAirline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.util.*;

public class Airline extends AbstractAirline<Flight> implements Serializable
{
  String name;

  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public Airline() {
  }


  private ArrayList<Flight> flights = new ArrayList<>();
  /**
   * Creates a new <code>Airline</code>
   *
   * @param name
   *        The name of the airline
   */
  public Airline(String name)
  {
    this.name=name;

  }


  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {

    this.flights.add((Flight) flight);
    Collections.sort(flights);
  }

  @Override
  public Collection<Flight> getFlights() {

    return this.flights;
  }

  /**
   * flightPrettyDump method returns flight details in a nicely formatted textual presentation.
   * @return String containing airline name,flight number, departure airport name, departure date and time,
   *         arrival airport name ,arrival date and time and duration of the flight in minutes.
   */
  public String flightPrettyDump()
  {
    // Create the copy of flights, compare each flight. If flights are equal then remove them from the list
    ArrayList<Flight> newList = (ArrayList<Flight>) flights.clone();

    // Iterate
    for (int i = 0; i < flights.size(); i++) {
      for (int j = flights.size() - 1; j >= i; j--) {
        if (i == j) {
          continue;
        }
        if (flights.get(i).equals(flights.get(j))) {
          newList.remove(flights.get(i));
          break;
        }
      }
    }
    Collections.sort(newList);
    String flightDetails="****************************** "+name+" Details *******************************\n";

    for(Flight flight: newList)
    {
      flightDetails+= "\t\tFlight Number:  "+flight.getNumber()+"\n"+ "\t\tFrom Airport:  "+AirportNames.getName(flight.getSource())+"\n"+
              "\t\tDeparture Time :  "+ flight.getDepartureStringPretty()+"\n\t\tTo Airport:  "
              + AirportNames.getName(flight.getDestination())+"\n\t\tArrival Time:  "+flight.getArrivalStringPretty()+"\n\t\tDuration:  "+flight.getDurationOfFlight()+" Minutes\n\n";

    }
    return flightDetails;
  }

  /**
   * equals method check the equality.It calls flight's equal method to check the equality of flights.
   * @return true if equal, else returns false
   */
  @Override
  public boolean equals(Object f) {
    if (this == f) return true;
    if (f == null || getClass() != f.getClass()) return false;

    Airline airline = (Airline) f;

    return ((flights != null ? !flights.equals(airline.flights) : airline.flights != null)&&
            (name != null ? !name.equals(airline.name) : airline.name != null));
  }

}