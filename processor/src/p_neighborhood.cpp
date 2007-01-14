#include "p_neighborhood.h"
#include "p_error.h"
#include "p_output.h"

pNeighborhood::pNeighborhood() : perm_val(0)
{

}

pNeighborhood::~pNeighborhood()
{
    release();
}

void pNeighborhood::release()
{   
    for( size_t i=0; i<list.size(); ++i )
    {
        if( list[i] ) list[i]->release();
        delete list[i];
    }
    list.clear();
}

int pNeighborhood::create( pSolution * solution, pMove * move )
{
    P_ASSERT( solution != NULL && move != NULL, "empty arguments" );

    int result;

    release();

    switch( move->current() )
    {
    case PM_PERM:
        result = do_perm( solution, perm_val );
        if( perm_val < 2 )
        {
            move->lock();
            perm_val++;
        }
        else
        {
            move->unlock();
            perm_val = 0;
        }
        return( result );
    case PM_SWITCH:
        return( do_switch( solution ) );
    case PM_RAND:
        return( do_rand( solution ) );
    }

    return( -1 );
}

pSolution * pNeighborhood::best_result( pMap * map )
{
    P_ASSERT( map != NULL, "empty argument" );
    if( list.size() == 0 ) return( NULL );
    if( list.size() == 1 ) return( list[0] );

    pSolution * best;
    best = list[0];

//    pOut->print( "p_%d cost: %2.2f\n", 0, list[0]->penalty( map ) );
    for( size_t i=1; i<list.size(); ++i )
    {
//        pOut->print( "p_%d cost: %2.2f\n", i, list[i]->penalty( map ) );
        if( list[i]->penalty( map ) < best->penalty( map ) )
        {
            best = list[i];
        }
    }

    return( new pSolution( best ) );
}

int pNeighborhood::do_perm( pSolution * sol, int value )
{
    P_ASSERT( sol != NULL, "empty argument " );

    pSolution * nsol;

    pOut->print("\n\n");

    for( size_t i=0; i<sol->list.size(); ++i )
    {
        nsol = new pSolution();
        for( size_t j=0; j<sol->list.size(); ++j )
        {
            if( j != i )
            {
                nsol->list.push_back( new pTransmitter( sol->list[j] ) );
                pOut->print( "%d; ", sol->list[j]->get_type() );
            }
            else
            {
                nsol->list.push_back( new pTransmitter( value ) );
                pOut->print( "%d; ", value );
            }
        }
        list.push_back( nsol );
        pOut->print( "\n" );
    }
    pOut->print("\n");

    return( 0 );
}

int pNeighborhood::do_switch( pSolution * sol )
{
    P_ASSERT( sol != NULL, "empty argument " );

    pSolution * nsol;

    pOut->print("\n\n");

    for( size_t i=0; i<sol->list.size()-1; ++i )
    {
        nsol = new pSolution();
        for( size_t j=0; j<sol->list.size(); ++j )
        {
            if( j == i )
            {
                nsol->list.push_back( new pTransmitter( sol->list[j+1] ) );
                pOut->print( "%d; ", sol->list[j+1]->get_type() );
            }
            else if( j == i+1 )
            {
                nsol->list.push_back( new pTransmitter( sol->list[j-1] ) );
                pOut->print( "%d; ", sol->list[j-1]->get_type() );
            }
            else
            {
                nsol->list.push_back( new pTransmitter( sol->list[j] ) );
                pOut->print( "%d; ", sol->list[j]->get_type() );
            }
        }
        pOut->print( "\n" );
        list.push_back( nsol );
    }
    pOut->print( "\n" );

    return( 0 );
}

int pNeighborhood::do_rand( pSolution * sol )
{

    P_ASSERT( sol != NULL, "empty argument " );

    pSolution * nsol;
    int val;

    pOut->print("\n\n");
    for( size_t i=0; i<sol->list.size()-1; ++i )
    {
        nsol = new pSolution();
        for( size_t j=0; j<sol->list.size(); ++j )
        {
            val = rand()%3;
            nsol->list.push_back( new pTransmitter( val ) );
            pOut->print( "%d; ", val );
        }
        pOut->print( "\n" );
        list.push_back( nsol );
    }
    pOut->print( "\n" );

    return( 0 );

}
