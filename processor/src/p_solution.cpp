#include <cstdlib>
#include "p_solution.h"
#include "p_error.h"
#include "p_output.h"

pSolution::pSolution() : size(0), vec(NULL)
{
}

pSolution::pSolution( pSolution * ptr )
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

bool pSolution::equals( pSolution * s )
{
    P_ASSERT( s != NULL, "empty argument" );

    if( s->size != size ) return( false );

    for( int i=0; i<size; ++i )
    {
        if( vec[i] != s->vec[i] )
        {
            return( false );
        }
    }

    return( true );
}

