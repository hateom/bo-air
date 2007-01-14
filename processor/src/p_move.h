#ifndef __P_MOVE_H__
#define __P_MOVE_H__

#define PM_PERM     0x0001
#define PM_SWITCH   0x0002
#define PM_RAND     0x0003

#include <vector>

class pMove
{
public:
    pMove();
    ~pMove();

    int  init();
    int  next();
    bool remaining();

    int current();

    void lock();
    void unlock();
    bool locked();

protected:
    std::vector<int> move;
    std::vector<int>::iterator curr;

    bool m_lock;
};

#endif // __P_MOVE_H__

