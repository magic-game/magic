using UnityEngine;
using System.Collections;
using MagicEngine;
using System.Collections.Generic;
using AssemblyCSharp;

public class EnemyController : MonoBehaviour, MagicEntity {

    Transform player;               // Reference to the player's position.
    //PlayerHealth playerHealth;      // Reference to the player's health.
   // EnemyHealth enemyHealth;        // Reference to this enemy's health.
    NavMeshAgent nav;               // Reference to the nav mesh agent.
   
	Rigidbody rb;
	private List<SpellEventListener> listeners;
	int health;
	public int attackRange;
	public GameObject explosionPrefab;
	public float fieldOfViewAngle;
	private Vector3 lastPlayerSighting;
	public float sightRange = 50.0f;
	private bool shouldAttackPlayer = false;

	private float castCooldown = 0;
	public float startCastCooldown = 3.0f;
	public SpellPool spellPool;
	private SpellTemplate template;
	public GameObject spellBall;
	private bool playerInSight;
	private bool inAttackRange;

	public float turnSpeed;

	// Use this for initialization
	void Start ()
	{
		health = 2;
		rb = GetComponent<Rigidbody> ();
		listeners = new List<SpellEventListener> ();
		lastPlayerSighting = transform.position;
		template = new FireBallTemplate (spellBall);
	}

    void Awake()
    {
        // Set up the references.
		player = GameObject.FindGameObjectWithTag("Player").transform;
       // playerHealth = player.GetComponent<PlayerHealth>();
       // enemyHealth = GetComponent<EnemyHealth>();
        nav = GetComponent<NavMeshAgent>();
		nav.updateRotation = true;
    }

	void FixedUpdate() 
	{
		shouldAttackPlayer = false;
		playerInSight = false;
		inAttackRange = false;
		Vector3 direction = player.transform.position - transform.position;
		float angle = Vector3.Angle(direction, transform.forward);
		if (angle < fieldOfViewAngle * 0.5f) {
			RaycastHit hit;

			if(Physics.Raycast(transform.position + transform.up, direction.normalized, out hit, sightRange))
			{
				if (hit.collider.gameObject.transform == player) {
					// ... the player is in sight.
					playerInSight = true;

					lastPlayerSighting = player.position;
					//transform.LookAt(player);
					if (Vector3.Distance(player.position, transform.position) < attackRange) {
						inAttackRange = true;
					}

				}
			}
		}
		if (!inAttackRange) {
			nav.SetDestination (lastPlayerSighting);
		} else {
			nav.SetDestination (transform.position);
		}
	}

	private void lookTowardPlayer() {
		Vector3 direction = (lastPlayerSighting - transform.position).normalized;
		transform.rotation = Quaternion.Slerp (transform.rotation, Quaternion.LookRotation(direction), turnSpeed * Time.deltaTime);

	}

    void Update()
    {
		if (inAttackRange) {
			lookTowardPlayer ();
			updateCasting ();
		}

		if (health <= 0) {
			Instantiate (explosionPrefab, transform.position, Quaternion.identity);
			Destroy (gameObject);
		}
    }

	private void updateCasting() {
		if (castCooldown > 0) {
			castCooldown = castCooldown - Time.deltaTime;
		}

		if (castCooldown <= 0) {
			spellPool.CastSpell (template, this);
			castCooldown = startCastCooldown;
		}
	}

	public Place getPlace() {
		Place place = new Place (transform);
		place.position.y = place.position.y * 0.8f;
		place.forward = (player.position - transform.position).normalized;
		return place;
	}

	public Rigidbody getRigidbody() {
		return rb;
	}

	void OnCollisionEnter (Collision col) {
		MagicEntity ent = col.gameObject.GetComponent<MagicEntity> ();
		this.sendEvent (SpellEventType.Collision, ent);
	}

	public void AddEventListener(SpellEventListener listener) {
		listeners.Add (listener);
	}

	private void sendEvent(SpellEventType type, MagicEntity entity) {
		foreach (SpellEventListener listener in listeners) {
			listener.HandleEvent (type, entity);
		}
	}

	public void takeDamage(int amount) {
		health = health - amount;
	}
}
