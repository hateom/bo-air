#ifndef __P_TRANSMITTER_H__
#define __P_TRANSMITTER_H__

class pBuilding;

class pTransmitter
{
public:
    pTransmitter();
    pTransmitter( int x, int y, int type );
    ~pTransmitter();

    float get_cost() const;
    int   get_type() const;
    int   get_range() const;
    int   get_x() const;
    int   get_y() const;

    void  set_pos( int x, int y );
    void  set_type( int type );

    int   in_range( pBuilding * building );

protected:
    int x, y, type;
};

#endif // __P_TRANSMITTER_H__

