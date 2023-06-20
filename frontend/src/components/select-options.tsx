import { ResourceSample } from "../utils/type";

type SelectOptionsProps<T> = 
{
    label : string;
    name: keyof T;
    resources : ResourceSample[];
    register : any;
    value : keyof ResourceSample;
    errors : any;
    defaultValue? : string | number;
}

function SelectOptions<T>({ label, name, resources, register, value, errors, defaultValue} : SelectOptionsProps<T>)
{
    return(
        <>
        {
            resources.length > 0 &&
            (
                <div className={`flex flex-col`}>

                    <label className="px-1">{label}</label>

                    <select defaultValue={defaultValue} className="rounded-lg h-7 px-2" name={name} {...register(name)}>
                    {
                        resources.map((resource) => <option  key={resource.id} value={resource[value]}> {resource.name} </option> )
                    }
                    </select>
                    {errors[name] && <span className="px-1 text-sm text-red-600">{errors[name].message}</span>}

                </div>
            )
        }
        </>
    )
}

export default SelectOptions;