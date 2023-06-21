import { ReactNode } from "react"

const OverflowContainer = ({ children } : { children: ReactNode }) =>
{
    return(

        <div className="flex flex-col h-4/5 gap-2 overflow-y-auto ">

            {children}

        </div>
    )
}

export default OverflowContainer;