import { useState, useCallback } from 'react'
export default () => {
    const [user, setUser] = useState({})
    const setUserData = useCallback((account) => {
        setUser({ account: account})
    }, [])
    return { user, setUserData }
}
