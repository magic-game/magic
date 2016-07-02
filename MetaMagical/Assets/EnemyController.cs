using UnityEngine;
using System.Collections;

public class EnemyController : MonoBehaviour {

    Transform player;               // Reference to the player's position.
    //PlayerHealth playerHealth;      // Reference to the player's health.
   // EnemyHealth enemyHealth;        // Reference to this enemy's health.
    NavMeshAgent nav;               // Reference to the nav mesh agent.

    private float height = 2.0f;

    void Awake()
    {
        // Set up the references.
        player = GameObject.FindGameObjectWithTag("Player").transform;
       // playerHealth = player.GetComponent<PlayerHealth>();
       // enemyHealth = GetComponent<EnemyHealth>();
        nav = GetComponent<NavMeshAgent>();
    }


    void Update()
    {
        nav.SetDestination(player.position);
        nav.speed = 3 + ((Mathf.Sin(Time.time * 2.2f) * 2));
    }
}
