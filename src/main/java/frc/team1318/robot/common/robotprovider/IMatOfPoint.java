package frc.team1318.robot.common.robotprovider;

import java.util.List;

public interface IMatOfPoint
{
    void alloc(int elemNumber);
    void fromArray(IPoint...a);
    IPoint[] toArray();
    void fromList(List<IPoint> lp);
    List<IPoint> toList();
	void release();
}