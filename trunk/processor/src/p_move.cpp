#include "p_move.h"

pMove::pMove() : m_lock(false)
{
}

pMove::~pMove()
{
}

void pMove::lock()
{
    m_lock = true;
}

void pMove::unlock()
{
    m_lock = false;
}

bool pMove::locked()
{
    return( m_lock );
}

int pMove::init()
{
    move.clear();
    move.push_back( PM_PERM );
    move.push_back( PM_SWITCH );
    move.push_back( PM_RAND );
    move.push_back( PM_PERM );
    move.push_back( PM_SWITCH );
    move.push_back( PM_RAND );
    move.push_back( PM_PERM );
    move.push_back( PM_SWITCH );
    move.push_back( PM_RAND );
    move.push_back( PM_PERM );
    move.push_back( PM_SWITCH );
    curr = move.begin();

    return( 0 );
}

int pMove::next()
{
    if( m_lock ) return( 0 );

    if( remaining() )
    {
        ++curr;
        return( 0 );
    }

    return( -1 );
}

bool pMove::remaining()
{
    return( curr != move.end() );
}

int pMove::current()
{
    return( *curr );
}

