#include <cstdlib>
#include "p_solution.h"
#include "p_error.h"
#include "p_output.h"

pSolution::pSolution()
{
}

pSolution::pSolution( pSolution * ptr )
{
    for( size_t i=0; i<ptr->list.size(); ++i )
    {
        list.push_back( new pTransmitter( ptr->list[i] ) );
    }
}

pSolution::pSolution( pSolution & rhs )
{
    for( size_t i=0; i<rhs.list.size(); ++i )
    {
        list.push_back( new pTransmitter( rhs.list[i] ) );
    }
}

pSolution::~pSolution()
{
    release();
}

void pSolution::release()
{
    for( size_t i=0; i<list.size(); ++i )
    {
        delete list[i];
    }
    list.clear();
}

void pSolution::init_random( pMap * map )
{
    P_ASSERT( map != NULL, "empty argument" );

    release();

    size_t size = map->transmitters.size();

    for( size_t i=0; i<size; ++i )
    {
        list.push_back( new pTransmitter( rand()%3==0?1:0 ) );
    }
}

float pSolution::penalty( pMap * map )
{
    P_ASSERT( map != NULL, "empty argument" );

    float sum = 0;

    for( size_t i=0; i<list.size(); ++i )
    {
        sum += list[i]->get_cost();
    }

    for( size_t i=0; i<map->buildings.size(); ++i )
    {
        for( size_t j=0; j<list.size(); ++j )
        {
            if( list[i]->in_range( map->buildings[i] ) )
            {
                sum -= map->buildings[i]->profit();
                break;
            }
        }
    }

    return( sum );
}

bool pSolution::equals( pSolution * s )
{
    P_ASSERT( s != NULL, "empty argument" );

    if( s->list.size() != list.size() ) return( false );

    for( size_t i=0; i<list.size(); ++i )
    {
        if( !list[i]->equals( s->list[i] ) )
        {
            return( false );
        }
    }

    return( true );
}

int pSolution::not_null()
{
    size_t size;
    int cnt = 0;
    size = list.size();
    for( size_t i=0; i<size; ++i )
    {
        if( list[i]->get_type() != 0 ) cnt++;
    }
    return( cnt );
}

