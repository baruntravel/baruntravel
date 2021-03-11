import React from 'react'

const HotplacePages = ({match}) => {
    const location = match.params.id;

    return (
        <div>
            <h1>{location} 핫플레이스</h1>
        </div>
    )
}

export default HotplacePages
