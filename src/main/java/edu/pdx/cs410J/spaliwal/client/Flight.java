package edu.pdx.cs410J.spaliwal.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractFlight;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

public class Flight extends AbstractFlight implements Comparable<Flight>,Serializable
{
  private String sourceCode;
  private String destinationCode;
  private int flightNo;
  private long duration;
  private String departTime;
  private String arriveTime;
  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public Flight() {

  }

  public Flight(int flightNo, String sourceCode, String departTime, String destinationCode, String arriveTime) {

    this.flightNo = flightNo;
    this.sourceCode = sourceCode;
    this.destinationCode = destinationCode;
    this.arriveTime = arriveTime;
    this.departTime = departTime;
  }

  @Override
  public int getNumber() {
    return flightNo;
  }

  @Override
  public String getSource() {
    return sourceCode;
  }

  @Override
  public Date getDeparture() {
    return parseDate(departTime);
  }

  public String getDepartureString() {
    return departTime;
  }

  public String getDestination() {
    return destinationCode;
  }

  public Date getArrival() {
    return parseDate(arriveTime);
  }

  public String getArrivalString() {
    return arriveTime;
  }

  public String getArrivalStringPretty() {
    return parsePrettyDate(parseDate(arriveTime));
  }

  public String getDepartureStringPretty() {
    return parsePrettyDate(parseDate(departTime));
  }

  public double getDurationOfFlight() {
    return ((parseDate(arriveTime).getTime() - parseDate(departTime).getTime())/ 1000) / 60;
  }

  private static Date parseDate(String strDate) {

    DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
    Date date = null;
    date = df.parse(strDate);
    return date;
  }

  private static String parsePrettyDate(Date strDate) {

    DateTimeFormat df = DateTimeFormat.getFormat("EEE, MMM d, yyyy hh:mm a ");
    String  date = null;
    date = df.format(strDate);
    return date;
  }


  @Override
  public int compareTo(Flight f) {
    if(this.getSource().compareTo((f).getSource()) !=0)
      return this.getSource().compareTo((f).getSource());
    else
      return this.getDeparture().compareTo((f).getDeparture());
  }

  /**
   * equals method check the equality.It calls flight's equal method to check the equality of flights.
   * @return true if equal, else returns false
   */
  @Override
  public boolean equals(Object f)
  {
    if (this == f) return true;
    if (f == null || getClass() != f.getClass()) return false;

    Flight flight = (Flight) f;
    if (sourceCode != null ? !sourceCode.equals(flight.sourceCode) : flight.sourceCode!= null) return false;
    if (departTime != null ? !departTime.equals(flight.departTime) : flight.departTime != null) return false;

    return true;
  }
}