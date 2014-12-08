/**
 * @brief Justine Car Window (logplayer)
 *
 * @file CarWindow.java
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version 0.0.10
 *
 * @section LICENSE
 *
 * Copyright (C) 2014 Norbert Bátfai, batfai.norbert@inf.unideb.hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @section DESCRIPTION
 *
 * Justine - this is a rapid prototype for development of Robocar City Emulator
 * Justine Car Window (log player for Robocar City Emulator)
 *
 */
package justine.robocar;

class WaypointPolice implements org.jxmapviewer.viewer.Waypoint
{

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointPolice ( double lat, double lon )
    {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition ( lat, lon );
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition()
    {
        return geoPosition;
    }
}

class WaypointGangster implements org.jxmapviewer.viewer.Waypoint
{

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointGangster ( double lat, double lon )
    {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition ( lat, lon );
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition()
    {
        return geoPosition;
    }
}

class Loc
{

    double lat;
    double lon;

    public Loc ( double lat, double lon )
    {

        this.lat = lat;
        this.lon = lon;

    }

}

public class CarWindow extends javax.swing.JFrame
{

    org.jxmapviewer.viewer.WaypointPainter<org.jxmapviewer.viewer.Waypoint> waypointPainter
        = new org.jxmapviewer.viewer.WaypointPainter<org.jxmapviewer.viewer.Waypoint>();
    org.jxmapviewer.viewer.GeoPosition[] geopos
        = new org.jxmapviewer.viewer.GeoPosition[4];
    org.jxmapviewer.JXMapViewer jXMapViewer
        = new org.jxmapviewer.JXMapViewer();
    java.util.Map<Long, Loc> lmap = null;
    java.io.File tfile = null;
    java.util.Random rnd = new java.util.Random();
    java.util.Scanner scan = null;

    javax.swing.Action paintTimer = new javax.swing.AbstractAction()
    {

        public void actionPerformed ( java.awt.event.ActionEvent event ) {

            java.util.Set<org.jxmapviewer.viewer.Waypoint> waypoints
                = new java.util.HashSet<org.jxmapviewer.viewer.Waypoint>();

            if ( scan != null ) {

                try {

                    int time = 0, size = 0;

                    time = scan.nextInt();
                    size = scan.nextInt();

                    long ref_from = 0, ref_to = 0;
                    int step = 0, maxstep = 1, type = 0;
                    double lat, lon;
                    double lat2, lon2;

                    for ( int i = 0; i < size; ++i ) {

                        ref_from = scan.nextLong();
                        ref_to = scan.nextLong();
                        maxstep = scan.nextInt();
                        step = scan.nextInt();
                        type = scan.nextInt();

                        lat = lmap.get ( ref_from ).lat;
                        lon = lmap.get ( ref_from ).lon;

                        lat2 = lmap.get ( ref_to ).lat;
                        lon2 = lmap.get ( ref_to ).lon;

                        if ( maxstep == 0 ) {
                            maxstep = 1;
                        }

                        lat += step * ( ( lat2 - lat ) / maxstep );
                        lon += step * ( ( lon2 - lon ) / maxstep );

                        if ( type == 1 ) {
                            waypoints.add ( new WaypointGangster ( lat, lon ) );
                        } else if ( type == 2 ) {
                            waypoints.add ( new WaypointPolice ( lat, lon ) );
                        } else {
                            waypoints.add ( new org.jxmapviewer.viewer.DefaultWaypoint ( lat, lon ) );
                        }

                    }

                    waypointPainter.setWaypoints ( waypoints );

                    jXMapViewer.repaint();
                    repaint();

                } catch ( java.util.InputMismatchException imE ) {

                    java.util.logging.Logger.getLogger (
                        CarWindow.class.getName() ).log ( java.util.logging.Level.SEVERE, "Hibás bemenet...", imE );

                } catch ( java.util.NoSuchElementException e ) {

                    java.util.logging.Logger.getLogger (
                        CarWindow.class.getName() ).log ( java.util.logging.Level.SEVERE, "Tervezett leállás: input végét kapott el a kivételkezelő." );

                    CarWindow.this.dispatchEvent (
                        new java.awt.event.WindowEvent ( CarWindow.this,
                                                         java.awt.event.WindowEvent.WINDOW_CLOSING ) );
                }

            }

        }

    };

    public CarWindow ( double lat, double lon, java.util.Map<Long, Loc> lmap, java.io.File logfile )
    {

        this.lmap = lmap;

        try {

            if ( logfile != null ) {
                this.scan = new java.util.Scanner ( logfile );
            } else {
                this.scan = new java.util.Scanner ( System.in );
            }
        } catch ( java.io.FileNotFoundException e ) {
            scan = null;
            java.util.logging.Logger.getLogger (
                CarWindow.class.getName() ).log ( java.util.logging.Level.SEVERE, null, e );
        }

        do {

            try {

                Thread.sleep ( 4000 );
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        } while ( !scan.hasNext() );

        org.jxmapviewer.viewer.TileFactoryInfo tfInfo = new org.jxmapviewer.OSMTileFactoryInfo();
        org.jxmapviewer.viewer.DefaultTileFactory dTileFactory
            = new org.jxmapviewer.viewer.DefaultTileFactory ( tfInfo );
        dTileFactory.setThreadPoolSize ( 8 );

        org.jxmapviewer.viewer.GeoPosition debrecen
            = new org.jxmapviewer.viewer.GeoPosition ( lat, lon );

        javax.swing.event.MouseInputListener mouseListener
            = new org.jxmapviewer.input.PanMouseInputListener ( jXMapViewer );
        jXMapViewer.addMouseListener ( mouseListener );
        jXMapViewer.addMouseMotionListener ( mouseListener );
        jXMapViewer.addMouseListener (
            new org.jxmapviewer.input.CenterMapListener ( jXMapViewer ) );
        jXMapViewer.addMouseWheelListener (
            new org.jxmapviewer.input.ZoomMouseWheelListenerCursor ( jXMapViewer ) );
        jXMapViewer.addKeyListener (
            new org.jxmapviewer.input.PanKeyListener ( jXMapViewer ) );

        jXMapViewer.setTileFactory ( dTileFactory );

        ClassLoader classLoader = this.getClass().getClassLoader();

        final java.awt.Image markerImg
            = new javax.swing.ImageIcon ( classLoader.getResource ( "logo1.png" ) ).getImage();
        final java.awt.Image markerImgPolice
            = new javax.swing.ImageIcon ( classLoader.getResource ( "logo2.png" ) ).getImage();
        final java.awt.Image markerImgGangster
            = new javax.swing.ImageIcon ( classLoader.getResource ( "logo3.png" ) ).getImage();

        waypointPainter.setRenderer (
        new org.jxmapviewer.viewer.WaypointRenderer<org.jxmapviewer.viewer.Waypoint>() {
            @Override
            public void paintWaypoint ( java.awt.Graphics2D g2d, org.jxmapviewer.JXMapViewer jXMapV,
            org.jxmapviewer.viewer.Waypoint w ) {

                java.awt.geom.Point2D point = jXMapV.getTileFactory().geoToPixel (
                                                  w.getPosition(), jXMapV.getZoom() );

                if ( w instanceof WaypointPolice ) {
                    g2d.drawImage ( markerImgPolice, ( int ) point.getX() - markerImgPolice.getWidth ( jXMapV ),
                                    ( int ) point.getY() - markerImgPolice.getHeight ( jXMapV ), null );

                } else if ( w instanceof WaypointGangster ) {
                    g2d.drawImage ( markerImgGangster, ( int ) point.getX() - markerImgGangster.getWidth ( jXMapV ),
                                    ( int ) point.getY() - markerImgGangster.getHeight ( jXMapV ), null );
                } else {
                    g2d.drawImage ( markerImg, ( int ) point.getX() - markerImg.getWidth ( jXMapV ),
                                    ( int ) point.getY() - markerImg.getHeight ( jXMapV ), null );
                }
            }
        } );

        jXMapViewer.setOverlayPainter ( waypointPainter );
        jXMapViewer.setZoom ( 9 );
        jXMapViewer.setAddressLocation ( debrecen );
        jXMapViewer.setCenterPosition ( debrecen );

        setTitle (
            "Justine - Car Window (log player for Robocar City Emulator, Robocar World Championshin in Debrecen)" );
        getContentPane()
        .add ( jXMapViewer );
        setSize (
            800, 600 );
        setDefaultCloseOperation ( javax.swing.JFrame.EXIT_ON_CLOSE );

        new javax.swing.Timer (
            200, paintTimer ).start();
    }

    public static void readMap ( java.util.Map<Long, Loc> lmap, String name )
    {

        java.util.Scanner scan;
        java.io.File file = new java.io.File ( name );

        long ref = 0;
        double lat = 0.0, lon = 0.0;
        try {

            scan = new java.util.Scanner ( file );

            while ( scan.hasNext() ) {

                ref = scan.nextLong();
                lat = scan.nextDouble();
                lon = scan.nextDouble();

                lmap.put ( ref, new Loc ( lat, lon ) );
            }

        } catch ( Exception e ) {

            java.util.logging.Logger.getLogger (
                CarWindow.class.getName() ).log ( java.util.logging.Level.SEVERE, "hibás noderef2GPS leképezés", e );

        }

    }

    public static void main ( String[] args )
    {

        final java.util.Map<Long, Loc> lmap = new java.util.HashMap<Long, Loc>();

        if ( args.length == 1 ) {

            readMap ( lmap, args[0] );

            javax.swing.SwingUtilities.invokeLater ( new Runnable() {
                public void run() {
                    new CarWindow ( 47.5467, 21.6389, lmap, null ).setVisible ( true );
                }
            } );

        } else if ( args.length == 2 ) {

            readMap ( lmap, args[0] );

            final java.io.File logfile = new java.io.File ( args[1] );

            javax.swing.SwingUtilities.invokeLater ( new Runnable() {
                public void run() {
                    new CarWindow ( 47.5467, 21.6389, lmap, logfile ).setVisible ( true );
                }
            } );

        } else {

            System.out.println ( "To use as a logplayer:\n java -jar target/justineroadwindow-0.0.1-jar-with-dependencies.jar lmap.txt traffic.txt" );
            System.out.println ( "       as an on-line player:\njava -jar target/justineroadwindow-0.0.1-jar-with-dependencies.jar lmap.txt traffic.txt" );
        }

    }

}