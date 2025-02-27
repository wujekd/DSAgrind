#!/bin/bash


# /$$$$$$$                      /$$                                     
#| $$__  $$                    | $$                                       
#| $$  \ $$  /$$$$$$  /$$$$$$$ | $$   /$$  /$$$$$$   /$$$$$$   /$$$$$$$   
#| $$$$$$$  |____  $$| $$__  $$| $$  /$$/ /$$__  $$ /$$__  $$ /$$_____/   
#| $$__  $$  /$$$$$$$| $$  \ $$| $$$$$$/ | $$$$$$$$| $$  \__/|  $$$$$$    
#| $$  \ $$ /$$__  $$| $$  | $$| $$_  $$ | $$_____/| $$       \____  $$   
#| $$$$$$$/|  $$$$$$$| $$  | $$| $$ \  $$|  $$$$$$$| $$       /$$$$$$$/   
#|_______/  \_______/|__/  |__/|__/  \__/ \_______/|__/      |_______/    
#                                                                         
#       /$$$$$$  /$$                               /$$   /$$     /$$                                                                                                              |  $$$$$$/                                                          
#      /$$__  $$| $$                              |__/  | $$    | $$                                                                                                         \______/                                                        
#     | $$  \ $$| $$  /$$$$$$   /$$$$$$   /$$$$$$  /$$ /$$$$$$  | $$$$$$$  /$$$$$$/$$$$ 
#     | $$$$$$$$| $$ /$$__  $$ /$$__  $$ /$$__  $$| $$|_  $$_/  | $$__  $$| $$_  $$_  $$
#     | $$__  $$| $$| $$  \ $$| $$  \ $$| $$  \__/| $$  | $$    | $$  \ $$| $$ \ $$ \ $$
#     | $$  | $$| $$| $$  | $$| $$  | $$| $$      | $$  | $$ /$$| $$  | $$| $$ | $$ | $$
#     | $$  | $$| $$|  $$$$$$$|  $$$$$$/| $$      | $$  |  $$$$/| $$  | $$| $$ | $$ | $$
#     |__/  |__/|__/ \____  $$ \______/ |__/      |__/   \___/  |__/  |__/|__/ |__/ |__/
#                    /$$  \ $$   
#                   |  $$$$$$/                                           
#                    \______/                                                                                                                                 


# debug mode to skip taking user inputs
debug=0
if [ "$debug" -eq 1 ]; then
    process_count=3
    resource_count=3
    
    # example values
    work=(  )
    max=( 6 4 3 3 2 2 4 3 3 )
    alloc=( 2 2 1 1 0 0 2 1 1 )

else
    read -p "Input the process count: " process_count
    read -p "Input the resource count: " resource_count

    echo "Input the allocated data for all the processes: "
    read -ra alloc
    echo "Input the max claimed use data for all the processes: "
    read -ra max

    echo "Enter the initial resource levels available in the system: "
    read -ra work
fi

need=()
#function to calculate need for all the processes based on max and alloc values 
init_need() {
  for (( i=0; i<process_count; i++ ))
  do
    for (( j=0; j<resource_count; j++ ))
    do
      index=$(( i*resource_count + j ))
      # need = max - alloc
      value=$(( max[index] - alloc[index] ))
      need[$index]=$value
    done
  done
}



#  __ _           _                                            
# / _(_)_ __   __| |  ___  ___  __ _ _   _  ___ _ __   ___ ___ 
#| |_| | '_ \ / _` | / __|/ _ \/ _` | | | |/ _ | '_ \ / __/ _ \
#|  _| | | | | (_| | \__ |  __| (_| | |_| |  __| | | | (_|  __/
#|_| |_|_| |_|\__,_| |___/\___|\__, |\__,_|\___|_| |_|\___\___|
#                                 |_|                      

find_safe_sequence(){
    local -a local_work=( "${work[@]}" )
    local -a local_need=( "${need[@]}" )
    local -a local_alloc=( "${alloc[@]}" )
    local -a p_status=()  # 0 = not finished, 1 = finished
    local -a safe_seq=()

    # Initialize p_status and safe_seq
    for (( i=0; i<process_count; i++ )); do
        p_status+=(0)
        safe_seq+=(0)
    done

    local count=0 # cout finished processes

    # Try to find a sequence that satisfies all processes
    while [ $count -lt $process_count ]; do

        local found_any=false

        for (( p=0; p<process_count; p++ ))
        do
            if [ ${p_status[$p]} -eq 0 ]; then
            # Check if need <= local_work for every resource
                local can_run=true
                for (( r=0; r<resource_count; r++ )) 
                do
                    local idx=$(( p*resource_count + r ))
                    if [ ${local_need[$idx]} -gt ${local_work[$r]} ]; then
                    can_run=false
                    break
                    fi
                done

                # If the process can run, allocate its resources
                if $can_run; then
                    for (( r=0; r<resource_count; r++ ))
                    do
                        local idx=$(( p*resource_count + r ))
                        local_work[$r]=$(( local_work[$r] + local_alloc[$idx] ))
                    done
                    safe_seq[$count]=$p
                    p_status[$p]=1
                    count=$(( count + 1 ))
                    found_any=true
                fi
            fi
        done

        # If we couldn't find any process to run in this iteration,
        # the system is not in a safe state!!!
        if ! $found_any; then
            echo "ERROR! System is NOT in a safe state."
            return 1
        fi
    done

    echo "System is in a SAFE state."
    echo -n "Safe sequence: "
    for pid in "${safe_seq[@]}"
    do
        echo -n "$pid "
    done
    echo
    return 0
}


#           _     _                                    
#  __ _  __| | __| |  _ __  _ __ ___   ___ ___ ___ ___ 
# / _` |/ _` |/ _` | | '_ \| '__/ _ \ / __/ _ / __/ __|
#| (_| | (_| | (_| | | |_) | | | (_) | (_|  __\__ \__ \
# \__,_|\__,_|\__,_| | .__/|_|  \___/ \___\___|___|___/
#                    |_|                    
add_process() {
    echo "Enter $resource_count integers for the new process's MAX needs, separated by spaces:"
    read -ra new_max  # read into array
    if [ ${#new_max[@]} -ne $resource_count ]; then
        echo "Invalid input. Must have exactly $resource_count integers."
        return
    fi

    echo "Enter $resource_count integers for the new process's ALLOC, separated by spaces:"
    read -ra new_alloc
    if [ ${#new_alloc[@]} -ne $resource_count ]; then
        echo "Invalid input. Must have exactly $resource_count integers."
        return
    fi

    # Append to max, alloc
    for (( i=0; i<resource_count; i++ )); do
        max+=( "${new_max[$i]}" )
        alloc+=( "${new_alloc[$i]}" )
    done

    # Increase process_count by 1
    process_count=$(( process_count + 1 ))

    # Recompute need to account for the new process
    init_need

    echo "New process added successfully!"
echo
}



#                 _        ___  
# _ __ ___   __ _(_)_ __  / \ \ 
#| '_ ` _ \ / _` | | '_ \| | | | SHOULD THERE BE A FUNCTION FOR THIS CODE?
#| | | | | | (_| | | | | | | | |
#|_| |_| |_|\__,_|_|_| |_| | | |
# The program starts here \_/_/


# calculate the need for the processes
init_need

# initial evaluation of the provided system snapshot
echo "Checking for the safe sequence..."
find_safe_sequence

while true; do
    echo
    echo "-- MENU --"
    echo "1 - Add a new process"
    echo "0 - exit"
    read -p "Choose an option: " choice
    echo

    case "$choice" in
        1)
            add_process
            if ! find_safe_sequence; then
                echo "closing the script"
                exit 0
            fi
            # check system
            ;;
        0)
            echo "Goodbye!"
            exit 0
            ;;
        *)
            echo "Invalid choice. Please try again."
            ;;
    esac

    echo

done