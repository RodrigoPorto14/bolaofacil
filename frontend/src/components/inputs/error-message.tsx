import { ReactNode } from "react";

const ErrorMessage = ( { children } : {children : ReactNode} ) =>
{
    return (

        <span className="px-1 text-sm text-red-600"> {children} </span>
    )
}

export default ErrorMessage;