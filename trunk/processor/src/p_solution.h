#ifndef __P_SOLUTION_H__
#define __P_SOLUTION_H__

class pSolution
{
public:
    pSolution();
    pSolution( unsigned int siz );
    pSolution( pSolution * ptr );
    pSolution( pSolution & rhs );
    virtual ~pSolution();

    pSolution & operator=( pSolution & sol );

    pSolution & swap( int i, int j );
    pSolution & dec( int i, int j );
    pSolution & inc( int i, int j );

    bool operator==( pSolution & rhs );
    bool operator!=( pSolution & rhs ) { return !operator==(rhs); }

//    pSolution * copy(); 

    void init( int max_value, int building_cnt = 0 );
    bool equals( pSolution * s );
    void release();

    int *           vec;
    unsigned int    size;
};

#endif // __P_SOLUTION_H__

