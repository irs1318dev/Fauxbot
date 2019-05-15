package frc.robot.common.robotprovider;

import org.opencv.core.*;

public class MatWrapper implements IMat
{
    final Mat wrappedObject;

    public MatWrapper(Mat wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public IMat adjustROI(int dtop, int dbottom, int dleft, int dright)
    {
        return new MatWrapper(this.wrappedObject.adjustROI(dtop, dbottom, dleft, dright));
    }

    @Override
    public void assignTo(IMat m, int type)
    {
        this.wrappedObject.assignTo(OpenCVProvider.unwrap(m), type);
    }

    @Override
    public void assignTo(IMat m)
    {
        this.wrappedObject.assignTo(OpenCVProvider.unwrap(m));
    }

    @Override
    public int channels()
    {
        return this.wrappedObject.channels();
    }

    @Override
    public int checkVector(int elemChannels, int depth, boolean requireContinuous)
    {
        return this.wrappedObject.checkVector(elemChannels, depth, requireContinuous);
    }

    @Override
    public int checkVector(int elemChannels, int depth)
    {
        return this.wrappedObject.checkVector(elemChannels, depth);
    }

    @Override
    public int checkVector(int elemChannels)
    {
        return this.wrappedObject.checkVector(elemChannels);
    }

    @Override
    public IMat clone()
    {
        return new MatWrapper(this.wrappedObject.clone());
    }

    @Override
    public IMat col(int x)
    {
        return new MatWrapper(this.wrappedObject.col(x));
    }

    @Override
    public IMat colRange(int startcol, int endcol)
    {
        return new MatWrapper(this.wrappedObject.colRange(startcol, endcol));
    }

    @Override
    public IMat colRange(IRange r)
    {
        return new MatWrapper(this.wrappedObject.colRange(OpenCVProvider.unwrap(r)));
    }

    @Override
    public int dims()
    {
        return this.wrappedObject.dims();
    }

    @Override
    public int cols()
    {
        return this.wrappedObject.cols();
    }

    @Override
    public void convertTo(IMat m, int rtype, double alpha, double beta)
    {
        this.wrappedObject.convertTo(OpenCVProvider.unwrap(m), rtype, alpha, beta);
    }

    @Override
    public void convertTo(IMat m, int rtype, double alpha)
    {
        this.wrappedObject.convertTo(OpenCVProvider.unwrap(m), rtype, alpha);
    }

    @Override
    public void convertTo(IMat m, int rtype)
    {
        this.wrappedObject.convertTo(OpenCVProvider.unwrap(m), rtype);
    }

    @Override
    public void copyTo(IMat m)
    {
        this.wrappedObject.copyTo(OpenCVProvider.unwrap(m));
    }

    @Override
    public void copyTo(IMat m, IMat mask)
    {
        this.wrappedObject.copyTo(OpenCVProvider.unwrap(m), OpenCVProvider.unwrap(mask));
    }

    @Override
    public void create(int rows, int cols, int type)
    {
        this.wrappedObject.create(rows, cols, type);
    }

    @Override
    public void create(ISize size, int type)
    {
        this.wrappedObject.create(OpenCVProvider.unwrap(size), type);
    }

    @Override
    public IMat cross(IMat m)
    {
        return new MatWrapper(this.wrappedObject.cross(OpenCVProvider.unwrap(m)));
    }

    @Override
    public long dataAddr()
    {
        return this.wrappedObject.dataAddr();
    }

    @Override
    public int depth()
    {
        return this.wrappedObject.depth();
    }

    @Override
    public IMat diag(int d)
    {
        return new MatWrapper(this.wrappedObject.diag(d));
    }

    @Override
    public IMat diag()
    {
        return new MatWrapper(this.wrappedObject.diag());
    }

    @Override
    public double dot(IMat m)
    {
        return this.wrappedObject.dot(OpenCVProvider.unwrap(m));
    }

    @Override
    public long elemSize()
    {
        return this.wrappedObject.elemSize();
    }

    @Override
    public long elemSize1()
    {
        return this.wrappedObject.elemSize1();
    }

    @Override
    public boolean empty()
    {
        return this.wrappedObject.empty();
    }

    @Override
    public IMat inv(int method)
    {
        return new MatWrapper(this.wrappedObject.inv(method));
    }

    @Override
    public IMat inv()
    {
        return new MatWrapper(this.wrappedObject.inv());
    }

    @Override
    public boolean isContinuous()
    {
        return this.wrappedObject.isContinuous();
    }

    @Override
    public boolean isSubmatrix()
    {
        return this.wrappedObject.isSubmatrix();
    }

    @Override
    public void locateROI(ISize wholeSize, IPoint ofs)
    {
        this.wrappedObject.locateROI(OpenCVProvider.unwrap(wholeSize), OpenCVProvider.unwrap(ofs));
    }

    @Override
    public IMat mul(IMat m, double scale)
    {
        return new MatWrapper(this.wrappedObject.mul(OpenCVProvider.unwrap(m), scale));
    }

    @Override
    public IMat mul(IMat m)
    {
        return new MatWrapper(this.wrappedObject.mul(OpenCVProvider.unwrap(m)));
    }

    @Override
    public void push_back(IMat m)
    {
        this.wrappedObject.push_back(OpenCVProvider.unwrap(m));
    }

    @Override
    public void release()
    {
        this.wrappedObject.release();
    }

    @Override
    public IMat reshape(int cn, int rows)
    {
        return new MatWrapper(this.wrappedObject.reshape(cn, rows));
    }

    @Override
    public IMat reshape(int cn)
    {
        return new MatWrapper(this.wrappedObject.reshape(cn));
    }

    @Override
    public IMat row(int y)
    {
        return new MatWrapper(this.wrappedObject.row(y));
    }

    @Override
    public IMat rowRange(int startrow, int endrow)
    {
        return new MatWrapper(this.wrappedObject.rowRange(startrow, endrow));
    }

    @Override
    public IMat rowRange(IRange r)
    {
        return new MatWrapper(this.wrappedObject.rowRange(OpenCVProvider.unwrap(r)));
    }

    @Override
    public int rows()
    {
        return this.wrappedObject.rows();
    }

    @Override
    public IMat setTo(IScalar s)
    {
        return new MatWrapper(this.wrappedObject.setTo(OpenCVProvider.unwrap(s)));
    }

    @Override
    public IMat setTo(IScalar value, IMat mask)
    {
        return new MatWrapper(this.wrappedObject.setTo(OpenCVProvider.unwrap(value), OpenCVProvider.unwrap(mask)));
    }

    @Override
    public IMat setTo(IMat value, IMat mask)
    {
        return new MatWrapper(this.wrappedObject.setTo(OpenCVProvider.unwrap(value), OpenCVProvider.unwrap(mask)));
    }

    @Override
    public IMat setTo(IMat value)
    {
        return new MatWrapper(this.wrappedObject.setTo(OpenCVProvider.unwrap(value)));
    }

    @Override
    public ISize size()
    {
        return new SizeWrapper(this.wrappedObject.size());
    }

    @Override
    public long step1(int i)
    {
        return this.wrappedObject.step1(i);
    }

    @Override
    public long step1()
    {
        return this.wrappedObject.step1();
    }

    @Override
    public IMat submat(int rowStart, int rowEnd, int colStart, int colEnd)
    {
        return new MatWrapper(this.wrappedObject.submat(rowStart, rowEnd, colStart, colEnd));
    }

    @Override
    public IMat submat(IRange rowRange, IRange colRange)
    {
        return new MatWrapper(this.wrappedObject.submat(OpenCVProvider.unwrap(rowRange), OpenCVProvider.unwrap(colRange)));
    }

    @Override
    public IMat submat(IRect roi)
    {
        return new MatWrapper(this.wrappedObject.submat(OpenCVProvider.unwrap(roi)));
    }

    @Override
    public IMat t()
    {
        return new MatWrapper(this.wrappedObject.t());
    }

    @Override
    public long total()
    {
        return this.wrappedObject.total();
    }

    @Override
    public int type()
    {
        return this.wrappedObject.type();
    }

    @Override
    public String dump()
    {
        return this.wrappedObject.dump();
    }

    @Override
    public int put(int row, int col, double... data)
    {
        return this.wrappedObject.put(row, col, data);
    }

    @Override
    public int put(int row, int col, float[] data)
    {
        return this.wrappedObject.put(row, col, data);
    }

    @Override
    public int put(int row, int col, int[] data)
    {
        return this.wrappedObject.put(row, col, data);
    }

    @Override
    public int put(int row, int col, short[] data)
    {
        return this.wrappedObject.put(row, col, data);
    }

    @Override
    public int put(int row, int col, byte[] data)
    {
        return this.wrappedObject.put(row, col, data);
    }

    @Override
    public int get(int row, int col, byte[] data)
    {
        return this.wrappedObject.get(row, col, data);
    }

    @Override
    public int get(int row, int col, short[] data)
    {
        return this.wrappedObject.get(row, col, data);
    }

    @Override
    public int get(int row, int col, int[] data)
    {
        return this.wrappedObject.get(row, col, data);
    }

    @Override
    public int get(int row, int col, float[] data)
    {
        return this.wrappedObject.get(row, col, data);
    }

    @Override
    public int get(int row, int col, double[] data)
    {
        return this.wrappedObject.get(row, col, data);
    }

    @Override
    public double[] get(int row, int col)
    {
        return this.wrappedObject.get(row, col);
    }

    @Override
    public int height()
    {
        return this.wrappedObject.height();
    }

    @Override
    public int width()
    {
        return this.wrappedObject.width();
    }
}