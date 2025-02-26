
#Bankers Algorithm

def findSafeSequence(work, max, alloc, need):
    
    process_count = len(max)
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
    print(f"the safe sequence is: {safe_seq}")
    
    return True


if __name__ =="__main__":
    
    # init for the given n and m
    columns = 3
    rows = 3
    
    # get the inputs from a file ****
    work = [3,3,2]
    max = [[6,4,3],[3,2,2],[4,3,3]]
    alloc = [[2,2,1],[1,0,0],[2,1,1]]
    
    # add input validation ****
    
    # calculate need ***
    need = [[max[i][j] - alloc[i][j] for j in range(len(max[0]))] for i in range(len(max))]

    # find a safe sequence for the initial system snapshot
    initCheck = findSafeSequence(work.copy(), max, alloc, need)
    
    option = "1" if initCheck else "0"
    
    while (option != "0"):
        #findSafeSequence(work, max, alloc, need)
        
        
        print("1 - add a process")
        print("0 - exit")
        option = input("insert choice: ")
        
        if (option == "1"):
            # create a new process
            np_max = list(map(int, input(f"enter {columns} integers to set the processes claimed max: ").split()))
            np_alloc = list(map(int, input(f"enter {columns} integers to set resources allocated to the new process: ").split()))
            
            max.append(np_max)
            alloc.append(np_alloc)
            need.append([np_max[i] - np_alloc[i] for i in range(len(np_max))])
            print("Added a new process to the system snapshot.")
            rows = len(max)
            check = findSafeSequence(work.copy(), max, alloc, need)
            
            
                
        elif (option != "0"):
            print("invalid input!")
    
    
    
    print("good bye!")