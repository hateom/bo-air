#include "p_transmitter.h"
#include "p_building.h"

#include <cmath>

pTransmitter::pTransmitter()
{
}

pTransmitter::pTransmitter( int nx, int ny, int ntype ) : 
    x(nx), y(ny), type(ntype)
{
}

pTransmitter::~pTransmitter()
{
}

float pTransmitter::get_cost() const
{
    return( 0.0f );
}

int pTransmitter::get_type() const
{
    return( type );
}

int pTransmitter::get_range() const
{
    return( 0 );
}

int pTransmitter::get_x() const
{
    return( x );
}

int pTransmitter::get_y() const
{
    return( y );
}

void pTransmitter::set_pos( int nx, int ny )
{
    x = nx;
    y = ny;
}

void pTransmitter::set_type( int ntype )
{
    type = ntype;
}


int pTransmitter::in_range( pBuilding * b )
{
    float dist = sqrtf( 
        (float)((x - b->get_x())*(x - b->get_x()) + 
                (y - b->get_y())*(y - b->get_y()))
    );

    return( dist <= get_range() );
}

