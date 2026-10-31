package com.poultryledger.repository;

import com.poultryledger.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.*;

public final class Repositories { private Repositories() {} }

interface PackageMarker {}

// Repository declarations live in individual public interfaces below.
