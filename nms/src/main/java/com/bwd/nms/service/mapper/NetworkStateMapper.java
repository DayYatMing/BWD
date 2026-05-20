package com.bwd.nms.service.mapper;

import com.bwd.nms.domain.NetworkState;
import com.bwd.nms.service.dto.NetworkStateDTO;

import java.time.Instant;

public class NetworkStateMapper {

    public NetworkStateDTO networkStateToNetworkStateDTO(NetworkState networkState) {
        return new NetworkStateDTO(networkState);
    }

    public NetworkState networkStateDTOToNetworkState(NetworkStateDTO networkStateDTO){
        NetworkState networkState = new NetworkState();
        networkState.setUpdatedby(networkStateDTO.getUpdatedby());
        networkState.setDateupdated(Instant.now());
        networkState.setNetworkimage(networkStateDTO.getNetworkimage());
        return networkState;
    }

}
