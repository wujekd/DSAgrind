

#Bankers Algorithm



def findSafeSequence(work, max, alloc, need):
    
    process_count = len(work)
    resource_count = len(max[0])
    
    p_status = [0] * process_count # is the process completed
    safe_seq = [0] * process_count
    
    count = 0
    
    while (count < process_count):
        
        found = False
        
        # iterate processess
        for process_id in range(process_count):
            
            # if the process isnt resolved
            if (p_status[process_id] == 0):
                #check if its need for all resources is less than curent work
                for res_id in range(resource_count):
                    if (need[process_id][res_id] > work[res_id]):
                        break
                
                if (res_id == resource_count - 1): # if all needs were satisfied
                    
                    # mark as finished, add allocated res to work, add to safe seq
                    for r in range(resource_count):
                        work[r] += alloc[process_id][r]
                    
                    safe_seq[count] = process_id
                    count += 1
                    p_status[process_id] = 1
                    
                    found = True
        if (found == False):
            print("System is not in a safe state")
            return False
                
    print("System is in a safe state.")
    print(safe_seq)
    
    return True


if __name__ =="__main__":
    
    work = [3,3,2]
    max = [[6,4,3],[3,2,2],[4,3,3]]
    alloc = [[2,2,1],[1,0,0],[2,1,1]]
    need = [[4,2,2],[2,2,2],[2,2,2]]
    
    
    findSafeSequence(work, max, alloc, need)
