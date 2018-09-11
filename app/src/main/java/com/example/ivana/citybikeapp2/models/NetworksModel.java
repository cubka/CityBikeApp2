package com.example.ivana.citybikeapp2.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NetworksModel implements Serializable {

    ArrayList<Networks> networks;

    public ArrayList<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<Networks> networks) {
        this.networks = networks;
    }
}
