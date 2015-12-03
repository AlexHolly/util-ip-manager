package de.alexholly.util

import java.net.NetworkInterface
import java.util.Enumeration
import java.net.InetAddress
import scala.xml.XML
import scala.io.Source
import java.net.URL
import java.util.ArrayList
import java.util.List
import scala.sys.process._
import scala.collection.JavaConversions._

object IPManager {

  //TESTS
  //  def main(args: Array[String]) {
  //
  //    //      println(IPManager.isIP("128.0.0.1"))
  //    //      println(IPManager.isIP("127.0.0.2"))
  //    //      println(IPManager.isIP("128.0.0.2"))
  //    //    println(IPManager.isIP("127.0.0.0"))
  //    println(IPManager.getLocalIP())
  //    println(IPManager.getInternetIP())
  //  }

  //////////////////////////////////////////////////////////
  
//TODO muss oberservable sein fuer UI
  var ips: List[String] = new ArrayList[String]()

  initIPS()
  def initIPS() {
    NetworkInterface.getNetworkInterfaces().foreach { networkInterface =>
      networkInterface.getInetAddresses.foreach { ipp =>
        val ip = ipp.getHostAddress().toString()
        if (isIP(ip.toString())) {
          ips.add(ip.toString())
        }
      }
    }
  }

  def getAllIPs(): List[String] = {
    ips
  }

  def getHostname(): String = {
    try {
      return java.net.InetAddress.getLocalHost().getHostName()
    } catch {
      case e: java.net.UnknownHostException =>
        return "hostname".!!
    }
  }

  def getLocalIP(): String = {
    ips.foreach { ip =>
      if (isIP(ip) && !(ip.startsWith("127") || ip.endsWith(".1"))) {
        return ip
      }
    }
    return null
  }

  def isIP(ip: String): Boolean = {
    ip.matches("[1-9][0-9]+.[0-9]+.[0-9]+.[0-9]+")
  }

  /*
   * Quelle: http://natip.tuxnet24.de/
   * 		 http://natip.tuxnet24.de/index.php?mode=xml
   * 
   */
  def getInternetIP(): String = {
    val source = Source.fromURL(new URL("http://natip.tuxnet24.de/index.php?mode=xml"))
    val node = XML.loadString(source.mkString)
    var internetIP = (node \ "ip-address").text
    return internetIP
  }

}
