#include "p_transmitter.h"
#include "p_building.h"
#include "p_cost_mgr.h"
#include "p_error.h"

#include <cmath>

pTransmitter::pTransmitter()
{
}

pTransmitter::pTransmitter( pTransmitter & rhs )
{
    x = rhs.x;
    y = rhs.y;
    type = rhs.type;
}

pTransmitter::pTransmitter( pTransmitter * ptr )
{
    x = ptr->x;
    y = ptr->y;
    type = ptr->type;
}

pTransmitter::pTransmitter( int ntype ) : type(ntype)
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
    return( pCostMgr::get_transmitter_cost( get_type() ) );
}

int pTransmitter::get_type() const
{
    return( type );
}

float pTransmitter::get_range() const
{
    return( pCostMgr::get_transmitter_range( get_type() ) );
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
    if( !get_type() ) return( 0 );

    float dist = sqrtf( 
        (float)((x - b->get_x())*(x - b->get_x()) + 
                (y - b->get_y())*(y - b->get_y()))
    );

    return( dist <= get_range() );
}

bool pTransmitter::equals( pTransmitter * t )
{
    P_ASSERT( t != NULL, "empty argument" );
    return( type == t->type );
}

