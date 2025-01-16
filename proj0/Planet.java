public class Planet {
    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;

    }

    public double calcDistance(Planet p){
        return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos) + (yyPos - p.yyPos)*(yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p){
        double G = 6.67e-11;
        double r = this.calcDistance(p);
        double r_square = r*r;
        double F = this.mass * p.mass * G / r_square;
        return F;

    }

    public double calcForceExertedByX(Planet p){
        double F = this.calcForceExertedBy(p);
        return F*(p.xxPos - this.xxPos)/this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
        double F = this.calcForceExertedBy(p);
        return F*(p.yyPos - this.yyPos)/this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] allP){
        double allx = 0.0;
        for (Planet p: allP){
            if (this.equals(p)){
                continue;
            }
            else{
                allx += this.calcForceExertedByX(p);
            }
        }
        return allx;
    }

    public double calcNetForceExertedByY(Planet[] allP){
        double ally = 0.0;
        for (Planet p: allP){
            if (this.equals(p)){
                continue;
            }
            else{
                ally += this.calcForceExertedByY(p);
            }
        }
        return ally;
    }

    public void update(double time, double x_force, double y_force) {
        double a_x = x_force / this.mass;
        double a_y = y_force / this.mass;
        this.xxVel = this.xxVel + time*a_x;
        this.yyVel = this.yyVel + time*a_y;
        this.xxPos = this.xxPos + time*this.xxVel;
        this.yyPos = this.yyPos + time*this.yyVel;

    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}
