#include "p_building.h"

pBuilding::pBuilding()
{
}

pBuilding::pBuilding( int nx, int ny, int pop ) :
    x(nx), y(ny), population(pop)
{
}

pBuilding::~pBuilding()
{
}

int pBuilding::get_x() const
{
    return( x );
}

int pBuilding::get_y() const
{
    return( y );
}

int pBuilding::get_population() const
{
    return( population );
}

void pBuilding::set_population( int pop )
{
    population = pop;
}

void pBuilding::set_pos( int nx, int ny )
{
    x = nx;
    y = ny;
}
