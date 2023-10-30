package com.backend.li.service;

import com.backend.li.model.TranzactiiEntity;
import com.backend.li.model.UserEntity;
import com.backend.li.repository.TranzactiiRepository;
import com.backend.li.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranzactiiService {

    @Autowired
    private TranzactiiRepository tranzactiiRepository;

    @Autowired
    private UserRepository userRepository;

    public TranzactiiEntity createTranzactii(TranzactiiEntity tranzactii) {
        UserEntity user = userRepository.findById(tranzactii.getUser().getId()).orElseThrow();
        if (tranzactii.getTip().equalsIgnoreCase("deposit")) {
            user.setBalance(user.getBalance().add(tranzactii.getSuma()));
        } else if (tranzactii.getTip().equalsIgnoreCase("withdraw")) {
            user.setBalance(user.getBalance().subtract(tranzactii.getSuma()));
        }
        userRepository.save(user);
        return tranzactiiRepository.save(tranzactii);
    }

    public List<TranzactiiEntity> getAllTranzactii() {
        return tranzactiiRepository.findAll();
    }

    public TranzactiiEntity getTranzactiiById(Integer id) {
        return tranzactiiRepository.findById(id).orElseThrow();
    }

    public TranzactiiEntity updateTranzactii(Integer id, TranzactiiEntity tranzactii) {
        TranzactiiEntity existingTranzactii = tranzactiiRepository.findById(id).orElseThrow();
        UserEntity user = userRepository.findById(tranzactii.getUser().getId()).orElseThrow();
        if (tranzactii.getTip().equalsIgnoreCase("deposit")) {
            user.setBalance(user.getBalance().add(tranzactii.getSuma().subtract(existingTranzactii.getSuma())));
        } else if (tranzactii.getTip().equalsIgnoreCase("withdraw")) {
            user.setBalance(user.getBalance().subtract(tranzactii.getSuma().subtract(existingTranzactii.getSuma())));
        }
        userRepository.save(user);
        existingTranzactii.setTip(tranzactii.getTip());
        existingTranzactii.setSuma(tranzactii.getSuma());
        existingTranzactii.setData(tranzactii.getData());
        return tranzactiiRepository.save(existingTranzactii);
    }

    public void deleteTranzactii(Integer id) {
        TranzactiiEntity existingTranzactii = tranzactiiRepository.findById(id).orElseThrow();
        UserEntity user = userRepository.findById(existingTranzactii.getUser().getId()).orElseThrow();
        if (existingTranzactii.getTip().equalsIgnoreCase("deposit")) {
            user.setBalance(user.getBalance().subtract(existingTranzactii.getSuma()));
        } else if (existingTranzactii.getTip().equalsIgnoreCase("withdraw")) {
            user.setBalance(user.getBalance().add(existingTranzactii.getSuma()));
        }
        userRepository.save(user);
        tranzactiiRepository.delete(existingTranzactii);
    }
}