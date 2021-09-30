public class NBody {
    public static double readRadius(String fileName){
        In in = new In(fileName);

        int n = in.readInt();
        double radius = in.readDouble();

        return radius;
    }

    public static Planet[] readPlanets(String fileName){
        In in = new In(fileName);
        int n = in.readInt();
        Planet[] planets = new Planet[n];
        double radius = in.readDouble();
        for(int i=0; i < n; i++){
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[i] = new Planet(xPos,yPos,xVel,yVel,mass,imgFileName);
        }
        return planets;

    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);

        String imageToDraw = "./images/starfield.jpg";
        StdDraw.setScale(- radius, radius);
        StdDraw.enableDoubleBuffering();
        double time = 0;
        while(time <= T){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for(int i=0; i<planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i=0; i<planets.length; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(1,1,imageToDraw, radius*2, radius*2);
            for(Planet planet: planets){
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);

        }
    }
}
