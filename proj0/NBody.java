public class NBody {

    public static double readRadius(String file){
        In in = new In(file);
        int num = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file){
        In p_file = new In(file);
        int num = p_file.readInt();
        double radius = p_file.readDouble();
        Planet[] all_p = new Planet[num];

        int i = 0;
        while (i < num) {
            double xP = p_file.readDouble();
            double yP = p_file.readDouble();
            double xV = p_file.readDouble();
            double yV = p_file.readDouble();
            double m = p_file.readDouble();
            String img = p_file.readString();
            all_p[i] = new Planet(xP, yP, xV, yV, m, img);
            i++;
        }
        return all_p;

    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] all_p = readPlanets(filename);

        /**
         * 初始化背景以及planet
         */
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0,0, "images/starfield.jpg");
        for (Planet p : all_p){
            p.draw();
        }

        StdDraw.enableDoubleBuffering();

        double time = 0.0;

        /**
         * dt的间隔更新画布
         */
        while (time < T){
            int len = all_p.length;
            double[] xForces = new double[len];
            double[] yForces = new double[len];

            /**
             * 获得当前时刻的受力状况
             */
            int i = 0;
            while (i < len) {
                xForces[i] = all_p[i].calcNetForceExertedByX(all_p);
                yForces[i] = all_p[i].calcNetForceExertedByY(all_p);
                i ++;
            }

            i = 0;
            while(i < len){
                all_p[i].update(dt, xForces[i], yForces[i]);
                i++;
            }

            StdDraw.setScale(-radius, radius);
            StdDraw.picture(0,0, "images/starfield.jpg");
            for (Planet p : all_p){
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time+=dt;

        }

        StdOut.printf("%d\n", all_p.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < all_p.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    all_p[i].xxPos, all_p[i].yyPos, all_p[i].xxVel,
                    all_p[i].yyVel, all_p[i].mass, all_p[i].imgFileName);
        }


    }
}
