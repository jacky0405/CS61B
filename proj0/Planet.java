public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet planet){
        double xPos = this.xxPos - planet.xxPos;
        double yPos = this.yyPos - planet.yyPos;
        double r = Math.sqrt(xPos*xPos + yPos*yPos);

        return r;
    }

    public double calcForceExertedBy(Planet planet){
        return G * this.mass * planet.mass / (calcDistance(planet)*calcDistance(planet));
    }

    public double calcForceExertedByX(Planet planet){
        double pos = planet.xxPos - this.xxPos;
        return calcForceExertedBy(planet) * pos / calcDistance(planet);
    }

    public double calcForceExertedByY(Planet planet){
        double pos = planet.yyPos - this.yyPos;
        return calcForceExertedBy(planet) * pos / calcDistance(planet);
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double sum = 0;
        for(Planet planet: planets){
            if(planet == this){
                continue;
            }
            sum += calcForceExertedByX(planet);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double sum = 0;
        for(Planet planet: planets){
            if(planet == this){
                continue;
            }
            sum += calcForceExertedByY(planet);
        }
        return sum;
    }

    public void update(double dt, double xForce, double yForce){
        double ax = xForce / this.mass;
        double ay = yForce / this.mass;
        this.xxVel += ax * dt;
        this.yyVel += ay * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "./images/"+imgFileName);
    }
}
