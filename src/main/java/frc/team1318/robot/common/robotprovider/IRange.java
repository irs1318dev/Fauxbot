package frc.team1318.robot.common.robotprovider;

public interface IRange
{
    int getStart();
    int getEnd();
    void set(double[] vals);
    int size();
    boolean empty();
    IRange intersection(IRange r1);
    IRange shift(int delta);
    IRange clone();
}