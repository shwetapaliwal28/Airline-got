package edu.pdx.cs410J.spaliwal.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.spaliwal.client.Airline;
import edu.pdx.cs410J.spaliwal.client.Flight;
import edu.pdx.cs410J.spaliwal.client.AirlineService;

import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  private HashMap<String, Airline> airlineMap = new HashMap<String,Airline>();
  Airline airline=null;

  @Override
  public HashMap displayAllFlights()
  {
    return airlineMap;
  }

  @Override
  public HashMap addFlight(String name,Flight f)
  {
    airline=airlineMap.get(name);
    if(airline !=null)
    {
      airline.addFlight(f);
    }
    else
    {
      airline=new Airline(name);
      airline.addFlight(f);
      airlineMap.put(name,airline);
    }
    return airlineMap;
  }

  @Override
  public HashMap searchFlight(String name,String src,String dest)
  {
    return airlineMap;
  }

}