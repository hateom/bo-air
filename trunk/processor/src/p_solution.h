#ifndef __P_SOLUTION_H__
#define __P_SOLUTION_H__

class pSolution
{
public:
    pSolution();
    pSolution( pSolution * ptr );
    pSolution( pSolution & rhs );
    virtual ~pSolution();

    bool equals( pSolution * s );
    void release();

    int * vec;
    int   size;
};

#endif // __P_SOLUTION_H__

