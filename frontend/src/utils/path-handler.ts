export const previousPath = (path : string) =>
{
    const lastIndex = path.lastIndexOf('/');
    return path.slice(0,lastIndex)
}