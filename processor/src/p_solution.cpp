#include <cstdlib>
#include "p_solution.h"
#include "p_error.h"
#include "p_output.h"
#include "p_cost_mgr.h"

pSolution::pSolution() : vec(NULL), size(0)
{
}

pSolution::pSolution( unsigned int siz ) : size(siz)
{
    vec = new int[size];
}

pSolution::pSolution( pSolution * ptr ) : vec(NULL), size(0)
{
    if( ptr->size )
    {
        vec = new int[ptr->size];
        size = ptr->size;
        memcpy( vec, ptr->vec, size*sizeof(int) );
    }
}

pSolution::pSolution( pSolution & rhs )
{
    if( rhs.size )
    {
        vec = new int[rhs.size];
        size = rhs.size;
        memcpy( vec, rhs.vec, size*sizeof(int) );
    }
}

pSolution::~pSolution()
{
    release();
}

void pSolution::release()
{
    if( size )
    {
        delete [] vec;
        vec = NULL;
        size = 0;
    }
}

void pSolution::init( int max_value )
{
    if( !size ) return;

    for( size_t i=0; i<size; ++i )
    {
        vec[i] = rand()%(max_value+1);
    }
}

bool pSolution::equals( pSolution * s )
{
    P_ASSERT( s != NULL, "empty argument" );
    P_ASSERT( vec != NULL, "empty solution" );

    if( s->size != size ) return( false );

    for( unsigned int i=0; i<size; ++i )
    {
        if( vec[i] != s->vec[i] )
        {
            return( false );
        }
    }

    return( true );
}

pSolution & pSolution::swap( int i, int j )
{
    int swp;

    swp = vec[i];
    vec[i] = vec[j];
    vec[j] = swp;

    return *this;
}

pSolution & pSolution::inc( int i, int j )
{
    if( vec[i] < pc::transmitter_type_count()-1 ) vec[i]++;
//    if( vec[j] < pc::transmitter_type_count()-1 ) vec[j]++;
    return *this;
}

pSolution & pSolution::dec( int i, int j )
{
    if( vec[i] > 0 ) vec[i]--;
//    if( vec[j] > 0 ) vec[j]--;
    return *this;
}

/*
pSolution * pSolution::copy()
{
    return new pSolution( this );
}
*/

pSolution & pSolution::operator=( pSolution & sol )
{
    if( size == 0 )
    {
        vec = new int[sol.size];
    }
    else if( size != sol.size )
    {
        delete [] vec;
        vec = new int[sol.size];
    }

    memcpy( vec, sol.vec, sizeof(int)*size );

    return( *this );
}

