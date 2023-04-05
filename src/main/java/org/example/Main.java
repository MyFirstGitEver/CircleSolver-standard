package org.example;

public class Main {
    public static void main(String[] args) {
//        CircleSolver solver = new CircleSolver(new Pair<>(3.0, 5.0), 1);
//
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));

//        CircleSolver solver = new CircleSolver(new Pair<>(4.0, 5.0), 2);
//
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(45));
//        System.out.println(solver.next(90));
//
//        System.out.println(solver.next(30));
//        System.out.println(solver.next(60));

//        CircleSolver solver = new CircleSolver(new Pair<>(4.0, 5.0), 2);
//        System.out.println(solver.next(120));

//        CircleSolver solver = new CircleSolver(new Pair<>(4.0, 5.0), 2);
//        System.out.println(solver.next(180));

//        CircleSolver solver = new CircleSolver(new Pair<>(4.0, 5.0), 2);
//        System.out.println(solver.next(315));
    }
}

class Pair<X, Y>{
    public X first;
    public Y second;

    public Pair(X x, Y y) {
        this.first = x;
        this.second = y;
    }

    @Override
    public String toString() {
        return "Pair(" +
                "first=" + first +
                ", second=" + second +
                ')';
    }
}

class CircleSolver{
    private final Pair<Double, Double> center, mark;
    private int firstTimeFactor = -1;
    private Pair<Double, Double> start; // point A and starting point
    private final double r;

    CircleSolver(Pair<Double, Double> center, double r){
        this.center = center;
        this.r = r;
        this.start = new Pair<>(center.first, center.second - r);
        this.mark = new Pair<>(start.first - center.first, start.second - center.second);
    }

    public Pair<Double, Double> next(double alpha){
        if(alpha > 180){
            next(180);
            return next(alpha - 180);
        }

        double a = start.first - center.first;
        double b = start.second - center.second;
        double cosAlpha = Math.cos(Math.toRadians(alpha));

        double A = 1 + (a * a)/(b * b);
        double R0 = r*r*cosAlpha/ b;
        double B = -2 * R0 * a / b;
        double C = R0*R0 - r*r;

        double x1;
        double y1;
        double x2;
        double y2;

        if(Math.abs(b) > 0.0001){
            x1 = Quadratic.getRoot1(A, B, C);
            y1 = (r*r*cosAlpha - a * x1) / b + center.second;
            x2 = Quadratic.getRoot2(A, B, C);
            y2 = (r*r*cosAlpha - a*x2) / b + center.second;
        }
        else{
            x1 = r*r*cosAlpha / a;
            y1 = Math.sqrt(r*r - x1*x1);
            y2 = -y1;
            x2 = x1;

            y1 += center.second;
            y2 += center.second;
        }

        x1 += center.first;
        x2 += center.first;

        double check1 = degree(x1, y1);
        double check2 = degree(x2, y2);

        if(check1 * firstTimeFactor >= check2 * firstTimeFactor){
            start = new Pair<>(x1, y1);
        }
        else{
            start = new Pair<>(x2, y2);
        }

        if(firstTimeFactor == -1){
            firstTimeFactor = 1;
        }

        return start;
    }

    private double degree(double x, double y){
        double term1 = x - center.first;
        double term2 = y - center.second;

        if(term1 == 0 && term2 == 0){
            return 0;
        }
        double cos = (mark.first * term1 + mark.second * term2) / (r * r);

        if(Math.abs(x - center.first) < 0.001 || x > center.first){
            return Math.toDegrees(Math.acos(cos));
        }
        else{
            return 360 - Math.toDegrees(Math.acos(cos));
        }
    }
}

class Quadratic {
    public static  double getRoot1(double x, double y, double z) {

        return ((-1*y) + (Math.sqrt((Math.pow(y, 2) - (4*x*z)))))/(2*x);
    }

    public static  double getRoot2(double x, double y, double z) {

        return ((-1*y) - (Math.sqrt((Math.pow(y, 2) - (4*x*z)))))/(2*x);
    }
}